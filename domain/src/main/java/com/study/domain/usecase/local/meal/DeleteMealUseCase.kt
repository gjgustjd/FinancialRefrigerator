package com.study.domain.usecase.local.meal

import com.study.domain.model.MealItem
import com.study.domain.repository.RefrigeratorRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class DeleteMealUseCase @Inject constructor(private val repository: RefrigeratorRepository) {
    suspend fun execute(scope: CoroutineScope,item: MealItem) =
        scope.launch(Dispatchers.IO) {
            repository.deleteMeal(item)
        }

    suspend operator fun invoke(scope: CoroutineScope,item:MealItem) =
        execute(scope,item)
}