package com.study.domain.usecase.ingredients

import com.study.domain.model.IngredientItem
import com.study.domain.repository.RefrigeratorRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class GetAllIngredientsUseCase @Inject constructor(private val repository: RefrigeratorRepository) {
    fun execute(scope: CoroutineScope,collectProcess:(list:List<IngredientItem>)->Unit) =
        scope.launch(Dispatchers.IO) {
            repository.getAllIngredientByFlow().collectLatest {
                collectProcess(it)
            }
        }

    operator fun invoke(scope: CoroutineScope,collectProcess:(list:List<IngredientItem>)->Unit) =
        execute(scope,collectProcess)
}