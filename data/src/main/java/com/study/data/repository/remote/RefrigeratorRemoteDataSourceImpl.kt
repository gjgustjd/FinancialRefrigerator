package com.study.data.repository.remote

import android.util.Log
import com.study.domain.model.WebLinkItem
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.*
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.Connection
import org.jsoup.Jsoup
import java.net.URLEncoder
import javax.inject.Singleton

@Singleton
class RefrigeratorRemoteDataSourceImpl:RefrigeratorRemoteDataSource {
    private val notIngredientsTextList =
        listOf(".", "?", "!", "레시피", "만드는법", "만드는 법", "먹는 법", "만들기", "요리")
    private val notRecipePostKeywords = listOf("맛집", "후기", "내돈내먹", "식당", "리뷰", "웨이팅", "포장", "밀키트")
    private val PATH = "https://search.daum.net/search?nil_suggest=btn&w=blog&lpp=10&DA=PGD&q="

    @OptIn(FlowPreview::class)
    override suspend fun getCrawlWebLinkItemFlow(keyword: String,endNum: Int, coroutineNum: Int): Flow<WebLinkItem> {
        val eachUnit = (endNum / coroutineNum)
        val flowList = arrayListOf<Flow<WebLinkItem>>()
        for (j in 0 until coroutineNum) {
            try {
                val currentPosition = eachUnit * j + 1
                val endPosition = currentPosition + (eachUnit - 1)
                flowList.add(runCrawler(keyword,currentPosition, endPosition))
                Log.i("crawlKeyword", "$currentPosition..$endPosition")
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return flowOf(*flowList.toTypedArray()).flattenMerge()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private suspend fun runCrawler(keyword:String, currentPosition: Int, range: Int) =
        flow {
            var job:Job?=null
            for (i in currentPosition..range) {
                if(!currentCoroutineContext().isActive)
                    return@flow
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
                            if (isLinkContainNotRecipePostKeywords(webLinkItem)) {
//                                    job = launch(Dispatchers.IO) {
//                                        if (checkIsRecipePost(webLinkItem)) {
//                                            logRecipes(webLinkItem)
//                                            trySend(webLinkItem)
//                                        }
//                                    }
                            } else {
                                if (checkIsRecipePost(webLinkItem)) {
                                    logRecipes(webLinkItem)
                                    emit(webLinkItem)
                                }
                            }
                        }
                } catch (e: Exception) {
                    Log.i("DaumCrawling Flow", e.toString())
                }
            }
//            awaitClose{
//                job?.cancel()
//            }
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

    private fun logRecipes(vo:WebLinkItem){
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

//                Log.i("DaumCrawling innerContent", innerDocs.toString())
                ingListTexts?.forEach {
                    Log.i("DaumCrawling ingredientElement", it)
                }
            }
        } catch (e: Exception) {
        }
    }
}