package com.study.presentation.v2.view.searchRecipe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.study.domain.model.WebLinkItem
import com.study.presentation.R
import com.study.presentation.databinding.ActivitySearchRecipesBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jsoup.Connection
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import java.net.URLEncoder

class CrawlRecipesActivity : AppCompatActivity() {
    val recyclerAdapter by lazy { RecyclerWebLinkAdapter() }
    var webLinkVOList = arrayListOf<WebLinkItem>()
    private val notIngredientsTextList =
        listOf(".", "?", "!", "레시피", "만드는법", "만드는 법", "먹는 법", "만들기", "요리")
    private val notRecipePostKeywords = listOf("맛집", "후기", "내돈내먹", "식당", "리뷰", "웨이팅", "포장", "밀키트")
    private val PATH = "https://search.daum.net/search?nil_suggest=btn&w=blog&lpp=10&DA=PGD&q="

    companion object IntentKey {
        const val SEARCH_TYPE = "type"
        const val SEARCH_KEYWORD = "keyword"
        const val TYPE_INGREDIENT = "ingredient"
        const val TYPE_RECIPE = "recipe"
    }

    lateinit var binding: ActivitySearchRecipesBinding
    lateinit var type: String
    lateinit var keyword: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search_recipes)
        binding.run {
            type = intent.getStringExtra(SearchRecipesActivity.SEARCH_TYPE) ?: ""
            keyword = intent.getStringExtra(SearchRecipesActivity.SEARCH_KEYWORD) ?: ""

            recyclerSearchRecipes.run {
                adapter = recyclerAdapter
                layoutManager = LinearLayoutManager(baseContext)
            }
            titleBar.run {
//                activity = this@CrawlRecipesActivity
                txtHomeTitle.text = getString(R.string.search_result, keyword)
            }
            webLinkVOList.clear()
            recyclerAdapter.notifyDataSetChanged()

            lifecycleScope.launch(Dispatchers.IO) {
                crawlKeywordAndGetFlows(100, 5).collect {
                    webLinkVOList.add(it)
                    withContext(Dispatchers.Main)
                    {
                        recyclerAdapter.setInsertItems(webLinkVOList)
                    }
                }
            }
        }
    }

    @OptIn(FlowPreview::class)
    private suspend fun crawlKeywordAndGetFlows(endNum: Int, coroutineNum: Int):Flow<WebLinkItem> {
        val eachUnit = (endNum / coroutineNum)
        val flowList = arrayListOf<Flow<WebLinkItem>>()
        for (j in 0 until coroutineNum) {
            try {
                val currentPosition = eachUnit * j + 1
                val endPosition = currentPosition + (eachUnit - 1)
                flowList.add(runCrawler(currentPosition, endPosition))
                Log.i("crawlKeyword", "$currentPosition..$endPosition")
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return flowOf(*flowList.toTypedArray()).flattenMerge()
    }

    private suspend fun runCrawler(currentPosition: Int, range: Int) =
            channelFlow {
                for (i in currentPosition..range) {
                    val searchKeyword = URLEncoder.encode(keyword, "UTF-8")
                    try {
                        Log.i("DaumCrawling Flow", "Started")
                        val doc =
                            Jsoup
                                .connect("$PATH$searchKeyword&p=$i")
                                .method(Connection.Method.GET)
                                .get()
                        doc.select("div:contains(레시피).cont_inner")
                            ?.forEach { it ->
                                val webLinkItem = parseWebLink(doc, it)
                                if (!webLinkVOList.contains(webLinkItem)) {
                                    if (isLinkContainNotRecipePostKeywords(webLinkItem)) {
                                        awaitClose {
                                            lifecycleScope.launch(Dispatchers.IO) {
                                                if (checkIsRecipePost(webLinkItem)) {
                                                    send(webLinkItem)
                                                }
                                            }
                                        }
                                    } else {
                                        if (checkIsRecipePost(webLinkItem)) {
                                            send(webLinkItem)
                                        }
                                    }
                                }
                            }
                    } catch (e: Exception) {
                        Log.i("DaumCrawling Flow", e.toString())
                    }
                }
            }

    private fun parseWebLink(doc: Document, element: Element): WebLinkItem {
        Log.i("DaumCrawling Parsing", "Started")
        val a = element.selectFirst("a[href]")

        val href = a?.attr("href") ?: ""
        val imgUrl = doc.selectFirst("a[href$=$href] img")?.attr("src") ?: ""
        val title = a?.text() ?: ""
        val desc = element.selectFirst("p")?.text() ?: ""

        return WebLinkItem(
            href = href,
            imageUrl = imgUrl,
            title = title,
            desc = desc
        )
    }

    private fun isLinkContainNotRecipePostKeywords(webLinkItem: WebLinkItem) = notRecipePostKeywords
        .any { webLinkItem.title.contains(it) || webLinkItem.desc.contains(it) }

    private fun isRecipePost(text: String) = listOf("재료", "레시피", "만들기", "따라하기")
        .any { text.contains(it) }

    private fun checkIsRecipePost(vo: WebLinkItem): Boolean {
        val innerDocs =
            Jsoup
                .connect(vo.href)
                .method(Connection.Method.GET)
                .get()
        return isRecipePost(innerDocs.toString())
    }

    private suspend fun checkIsRecipePostAndAdd(vo: WebLinkItem) {
        try {
            Log.i("DaumCrawling isRecipePost", "Started")
            if (checkIsRecipePost(vo)) {
                val innerDocs =
                    Jsoup
                        .connect(vo.href)
                        .method(Connection.Method.GET)
                        .get()
                val ownIngredientsElement = innerDocs.getElementsContainingOwnText("재료").first()
                val ingredientsElements = ownIngredientsElement?.parent()
                    ?.getElementsByIndexGreaterThan(ownIngredientsElement.siblingIndex() - 1)
                val ingListTexts =
                    ingredientsElements?.text()
                        ?.split(",")
                        ?.filter { txt ->
                            txt.run {
                                length <= 20 &&
                                        this !in notIngredientsTextList &&
                                        isNotBlank()
                            }
                        }
                        ?.distinct()
                        ?.onEach { it.trim() }

                Log.i("DaumCrawling innerContent", innerDocs.toString())
                ingListTexts?.forEach {
                    Log.i("DaumCrawling ingredientElement", it)
                }
                webLinkVOList.add(vo)
                withContext(Dispatchers.Main)
                {
                    recyclerAdapter.setInsertItems(webLinkVOList)
                }
            }
        } catch (e: Exception) {
        }
    }
}