package com.study.domain.usecase.remote

import com.study.domain.repository.RefrigeratorRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class getCrawlRecipeWebLinkUseCase @Inject constructor(private val repository: RefrigeratorRepository) {
    suspend fun execute(scope: CoroutineScope, keyword: String, endNum: Int, coroutineNum: Int) =
        scope.launch(Dispatchers.IO) {
            repository.getCrawlWebLinkItemFlow(keyword, endNum, coroutineNum)
        }

    suspend operator fun invoke(
        scope: CoroutineScope,
        keyword: String,
        endNum: Int,
        coroutineNum: Int
    ) =
        execute(scope, keyword, endNum, coroutineNum)

}