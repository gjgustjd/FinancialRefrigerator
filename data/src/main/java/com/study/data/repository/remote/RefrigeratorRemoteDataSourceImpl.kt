package com.study.data.repository.remote

import android.util.Log
import com.study.domain.model.WebLinkItem
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.*
import org.jsoup.Connection
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import java.net.URLEncoder
import java.util.regex.Pattern
import javax.inject.Singleton

@Singleton
class RefrigeratorRemoteDataSourceImpl : RefrigeratorRemoteDataSource {
    private val notIngredientsTextList =
        listOf(".", "?", "!", "레시피", "만드는법", "만드는 법", "먹는 법", "만들기", "요리", "재료", "조리", "순서")
    private val ingeredientUnitRegexList =
        listOf(
            "(\\d+(\\/\\d+)?|한|두|세|네|다섯|여섯|일곱|여덟|아홉|열|반)\\s?(개|꼬집|숟가락|숟갈|스푼|(티|테이블)\\s?스푼|(큰|작은)\\s?술|통|조각|움큼|주먹|근|묶음|봉지|장|공기)",
            "(\\d+)(\\/\\d+)?\\s?([mMkK]?[lLgG]|[tT]([bB]?[sS])?|[cC]c|cup)"
        )
    private val notRecipePostKeywords = listOf("맛집", "후기", "내돈내먹", "식당", "리뷰", "웨이팅", "포장", "밀키트")
    private val PATH = "https://search.daum.net/search?nil_suggest=btn&w=blog&lpp=10&DA=PGD&q="

    @OptIn(FlowPreview::class)
    override suspend fun getCrawlWebLinkItemFlow(
        keyword: String,
        endNum: Int,
        flowNum: Int
    ): Flow<WebLinkItem> {
        val progression = getDividedProgressions(endNum, flowNum)
        val flowArray = progression.map {
            runCrawler(keyword, it until it + progression.step)
        }.toTypedArray()
        return flowOf(*flowArray).flattenMerge()
    }

    private fun getDividedProgressions(num: Int, dividend: Int) = (1..num step (num / dividend))

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun runCrawler(keyword: String, range: IntRange) =
        callbackFlow {
            var job: Job? = null
            for (i in range) {
                currentCoroutineContext().ensureActive()
                val searchKeyword = URLEncoder.encode(keyword, "UTF-8")
                Log.i("DaumCrawling Flow", "Started")
                getJSoupDocument("$PATH$searchKeyword&p=$i")
                    ?.apply {
                        select("div:contains(레시피).cont_inner")
                            ?.forEach { it ->
                                val webLinkItem = parseWebLink(this, it)
                                val checkProcess = {
                                    if (checkIsRecipePost(webLinkItem)) {
                                        logRecipes(webLinkItem)
                                        trySend(webLinkItem)
                                    }
                                }

                                if (isLinkContainNotRecipePostKeywords(webLinkItem)) {
                                    job = launch(Dispatchers.IO) { checkProcess() }
                                } else {
                                    checkProcess()
                                }
                            }
                    }
            }
            awaitClose {
                job?.cancel()
            }
        }.flowOn(Dispatchers.IO).catch { e -> Log.i("DaumCrawling Flow", e.toString()) }

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
        return getJSoupDocument(vo.href)?.let {
            isRecipePost(it.toString())
        } ?: false
    }

    private fun getJSoupDocument(link: String): Document? {
        return try {
            Jsoup
                .connect(link)
                .method(Connection.Method.GET)
                .get()
        } catch (e: Exception) {
            Log.i("DaumCrawling Error", e.toString())
            null
        }
    }

    private fun logRecipes(vo: WebLinkItem) {
        Log.i("DaumCrawling isRecipePost", "Started")
        if (checkIsRecipePost(vo)) {
            val ingredientsList = arrayListOf<String>()
            val innerDocs = getJSoupDocument(vo.href)
            val ownIngredientsElement = innerDocs?.getElementsContainingOwnText("재료")?.first()
            val ingredientsElements =
                if (ownIngredientsElement?.siblingElements()?.isEmpty() == true)
                    ownIngredientsElement.parent()?.nextElementSiblings()
                else
                    ownIngredientsElement?.nextElementSiblings()

            if (ownIngredientsElement?.text()
                    ?.contains(ingeredientUnitRegexList[0].toRegex()) == true
            )
                ingredientsList.addAll(ownIngredientsElement.text().split(","))

            val ingredientElementsText = ingredientsElements
                ?.flatMap { it.text().split("[,:]".toRegex()) }
                ?.onEach(String::trim)
                ?.asSequence()
                ?.takeWhile { it -> notIngredientsTextList.none(it::contains) }
                ?.filter(String::isNotEmpty)
                ?.filter(::isIngredientText)
                ?.distinct()
                ?.toList()!!
//                ?.fold("") { total, next -> total + " " + next.text() }
//                ?.split(",")
//                ?.filter(::isIngredientText)
//                ?.distinct()
//                ?.onEach { Log.i("DaumCrawling ingredientElement", it.trim()) }

            ingredientsList.addAll( ingredientElementsText )
            try {
                val matcher = Pattern.compile("\\S+\\s?"+ingeredientUnitRegexList[0]).matcher(ingredientsElements.text())
                while (matcher.find())
                {
                    Log.i("DaumCrawling finded", matcher.group())
                }
            }catch (e:Exception)
            {
                Log.i("DaumCrawling finded", e.toString())
            }

            ingredientsList.onEach { Log.i("DaumCrawling ingredientElement", it) }

            if(ingredientsList.isEmpty())
            {
                Log.i("DaumCrawling ingredientError", "innerDocs:${innerDocs?.text()}")
                Log.i("DaumCrawling ingredientError", "ownIngredientsElement:${ownIngredientsElement?.text()?:"null"}")
                Log.i("DaumCrawling ingredientError", "ingredientsElements:${ingredientsElements.text()}")
                Log.i("DaumCrawling ingredientError", "ingredientsElementsText:${ingredientElementsText}")
            }
        }
    }

    private fun isElementContainIngredientText(element: Element) =
        isIngredientText(element.text())

    private fun isIngredientText(text: String): Boolean = text.run {
        length <= 20 &&
                notIngredientsTextList.none(::contains) &&
                isNotBlank()
    }
}