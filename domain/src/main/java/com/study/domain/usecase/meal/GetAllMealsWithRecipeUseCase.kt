package com.study.domain.usecase.meal

import com.study.domain.model.MealAndRecipeItem
import com.study.domain.repository.RefrigeratorRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class GetAllMealsWithRecipeUseCase @Inject constructor(private val repository: RefrigeratorRepository) {
    suspend fun execute(scope: CoroutineScope,collectProcess:(list:List<MealAndRecipeItem>)->Unit) =
        scope.launch(Dispatchers.IO) {
            repository.getAllMealsWithRecipeAsFlow().collectLatest {
                collectProcess(it)
            }
        }

    suspend operator fun invoke(scope: CoroutineScope,collectProcess:(list:List<MealAndRecipeItem>)->Unit) =
        execute(scope,collectProcess)
}