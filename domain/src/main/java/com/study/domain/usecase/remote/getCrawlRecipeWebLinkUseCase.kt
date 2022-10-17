package com.study.domain.usecase.remote

import com.study.domain.repository.RefrigeratorRepository
import javax.inject.Inject

class GetCrawlRecipeWebLinkUseCase @Inject constructor(private val repository: RefrigeratorRepository) {
    suspend fun execute( keyword: String, endNum: Int, coroutineNum: Int) =
            repository.getCrawlWebLinkItemFlow(keyword, endNum, coroutineNum)

    suspend operator fun invoke(
        keyword: String,
        endNum: Int,
        coroutineNum: Int
    ) =
        execute(keyword, endNum, coroutineNum)

}