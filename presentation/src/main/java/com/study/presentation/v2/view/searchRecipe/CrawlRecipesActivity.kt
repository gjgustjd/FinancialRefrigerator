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
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jsoup.Connection
import org.jsoup.Jsoup
import java.net.URLEncoder

class CrawlRecipesActivity : AppCompatActivity() {
    val recyclerAdapter by lazy { RecyclerWebLinkAdapter() }
    var webLinkVOList = arrayListOf<WebLinkItem>()
    private val notIngredientsTextList = listOf(
        ".",
        "?",
        "!",
        "레시피",
        "만드는법",
        "만드는 법",
        "먹는 법",
        "만들기",
        "요리",
    )
    companion object IntentKey{
        const val SEARCH_TYPE="type"
        const val SEARCH_KEYWORD="keyword"
        const val TYPE_INGREDIENT="ingredient"
        const val TYPE_RECIPE="recipe"
    }

    lateinit var binding: ActivitySearchRecipesBinding
    lateinit var type:String
    lateinit var keyword:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search_recipes)
        binding.run{
            type = intent.getStringExtra(SearchRecipesActivity.SEARCH_TYPE) ?: ""
            keyword = intent.getStringExtra(SearchRecipesActivity.SEARCH_KEYWORD) ?: ""

            recyclerSearchRecipes.run{
                adapter = recyclerAdapter
                layoutManager = LinearLayoutManager(baseContext)
            }
            titleBar.run{
//                activity = this@CrawlRecipesActivity
                txtHomeTitle.text = getString(R.string.search_result,keyword)
            }
            crawlKeyword(100, 5)
        }
    }

    private fun crawlKeyword(endNum: Int, coroutineNum: Int) {
        webLinkVOList.clear()
        recyclerAdapter.notifyDataSetChanged()
        val eachUnit = (endNum / coroutineNum)
        for (j in 0 until coroutineNum) {
            try {
                val currentPosition = eachUnit * j + 1
                val endPosition = currentPosition + (eachUnit - 1)
                runCrawler(currentPosition, endPosition)
                Log.i("crawlKeyword", "$currentPosition..$endPosition")
            }catch (e:Exception)
            {
                e.printStackTrace()
            }
        }
    }

    private fun runCrawler(currentPosition: Int, range: Int) {
        lifecycleScope.launch(Dispatchers.IO) {
            for (i in currentPosition..range) {
                    val searchKeyword = URLEncoder.encode(keyword, "UTF-8")
                    val doc =
                        Jsoup
                            .connect("https://search.daum.net/search?nil_suggest=btn&w=blog&lpp=10&DA=PGD&q=$searchKeyword&p=$i")
                            .method(Connection.Method.GET)
                            .get()
//                Log.i("DaumCrawling All", doc.toString())
                    val mightNotRecipePosts = arrayListOf<WebLinkItem>()
                    doc.select("div:contains(레시피).cont_inner")
                        ?.forEach {
                            Log.i("DaumCrawling Parsing", "Started")
                            val a = it.selectFirst("a[href]")

                            val href = a?.attr("href") ?: ""
                            val imgUrl = doc.selectFirst("a[href$=$href] img")?.attr("src") ?: ""
                            val title = a?.text() ?: ""
                            val desc = it.selectFirst("p")?.text() ?: ""
                            val webLinkItem =
                                WebLinkItem(
                                    href = href,
                                    imageUrl = imgUrl,
                                    title = title,
                                    desc = desc
                                )
                            val isMightNotBeRecipePost =
                                listOf("맛집", "후기", "내돈내먹", "식당", "리뷰", "웨이팅", "포장", "밀키트")
                                    .any { title.contains(it) || desc.contains(it) }

                            Log.i("DaumCrawling Parsing", "Ended")
                            if (isMightNotBeRecipePost) {
                                mightNotRecipePosts.add(webLinkItem)
                            } else {
                                checkIsRecipePostAndAdd(webLinkItem)
                            }
                        }

                    mightNotRecipePosts.forEach {
                        checkIsRecipePostAndAdd(it)
                    }
            }
        }
    }

    private fun isRecipePost(text: String) = listOf("재료", "레시피", "만들기", "따라하기")
        .any { text.contains(it) }

    private suspend fun checkIsRecipePostAndAdd(vo: WebLinkItem) {
        try {
            Log.i("DaumCrawling isRecipePost", "Started")
            val innerDocs =
                Jsoup
                    .connect(vo.href)
                    .method(Connection.Method.GET)
                    .get()
            if (isRecipePost(innerDocs.toString())) {
                val ownIngredientsElement = innerDocs.getElementsContainingOwnText("재료").first()
                val ingredientsElements = ownIngredientsElement?.parent()
                    ?.getElementsByIndexGreaterThan(ownIngredientsElement.siblingIndex() - 1)
                val ingListTexts =
                    ingredientsElements?.text()
                        ?.split(",")
                        ?.filter { txt ->
                            txt.length <= 20 &&
                                    txt !in notIngredientsTextList &&
                                    txt.isNotBlank()
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