package com.study.domain.usecase.local.ingredients

import com.study.domain.model.IngredientItem
import com.study.domain.repository.RefrigeratorRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class UpdateIngredientUseCase @Inject constructor(private val repository: RefrigeratorRepository) {
    fun execute(scope:CoroutineScope,item:IngredientItem) =
        scope.launch(Dispatchers.IO) {
            repository.updateIngredients(item)
        }

    operator fun invoke(scope:CoroutineScope, item: IngredientItem) =
        execute(scope,item)
}