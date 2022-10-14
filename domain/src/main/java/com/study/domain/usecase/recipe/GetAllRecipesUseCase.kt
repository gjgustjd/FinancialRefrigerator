package com.study.domain.usecase.recipe

import com.study.domain.repository.RefrigeratorRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetAllRecipesUseCase @Inject constructor(private val repository: RefrigeratorRepository) {
    suspend fun execute(scope: CoroutineScope) =
        withContext(scope.coroutineContext + Dispatchers.IO) {
            repository.getAllRecipe()
        }

    suspend operator fun invoke(scope: CoroutineScope) =
        execute(scope)
}