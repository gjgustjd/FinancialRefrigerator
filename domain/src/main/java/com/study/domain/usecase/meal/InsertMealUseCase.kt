package com.study.domain.usecase.meal

import com.study.domain.model.RecipeItem
import com.study.domain.repository.RefrigeratorRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class InsertMealUseCase @Inject constructor(private val repository: RefrigeratorRepository) {
    suspend fun execute(scope: CoroutineScope,id:Int) =
        scope.launch(Dispatchers.IO) {
            repository.insertMeal(id)
        }

    suspend operator fun invoke(scope: CoroutineScope,id:Int) =
        execute(scope,id)
}