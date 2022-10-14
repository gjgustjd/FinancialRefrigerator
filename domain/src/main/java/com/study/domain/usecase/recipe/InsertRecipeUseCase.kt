package com.study.domain.usecase.recipe

import com.study.domain.model.RecipeItem
import com.study.domain.repository.RefrigeratorRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class InsertRecipeUseCase @Inject constructor(private val repository: RefrigeratorRepository) {
    suspend fun execute(scope: CoroutineScope,item:RecipeItem) =
        scope.launch(Dispatchers.IO) {
            repository.insertRecipe(item)
        }

    suspend operator fun invoke(scope: CoroutineScope,item:RecipeItem) =
        execute(scope,item)
}