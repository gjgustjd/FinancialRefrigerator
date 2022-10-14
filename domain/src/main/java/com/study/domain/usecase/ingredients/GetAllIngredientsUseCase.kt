package com.study.domain.usecase.ingredients

import com.study.domain.repository.RefrigeratorRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetAllIngredientsUseCase @Inject constructor(private val repository: RefrigeratorRepository) {
    suspend fun execute(scope: CoroutineScope) =
        withContext(scope.coroutineContext + Dispatchers.IO) {
            repository.getAllIngredientByFlow()
        }

    suspend operator fun invoke(scope: CoroutineScope) =
        execute(scope)
}