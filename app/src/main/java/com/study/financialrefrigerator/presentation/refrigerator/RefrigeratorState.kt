package com.study.financialrefrigerator.presentation.refrigerator

import com.study.financialrefrigerator.model.ingredient.IngredientItem

sealed class RefrigeratorState {
    object UnInitialize :RefrigeratorState()

    object Loading : RefrigeratorState()

    data class Success(
        val recipeList: List<IngredientItem>
    ) : RefrigeratorState()

    object Delete : RefrigeratorState()
    object Error : RefrigeratorState()
}