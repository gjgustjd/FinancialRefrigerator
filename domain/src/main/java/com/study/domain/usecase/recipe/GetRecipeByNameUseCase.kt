package com.study.domain.usecase.recipe

import com.study.domain.repository.RefrigeratorRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetRecipeByNameUseCase @Inject constructor(private val repository: RefrigeratorRepository) {
    suspend fun execute(scope:CoroutineScope, word:String) =
        withContext(scope.coroutineContext + Dispatchers.IO) {
            repository.getRecipeByName(word)
        }

    suspend operator fun invoke(scope:CoroutineScope, word:String) =
        execute(scope,word)
}