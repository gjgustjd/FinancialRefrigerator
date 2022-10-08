package com.study.presentation.old.presentation.refrigerator

import com.study.presentation.old.model.ingredient.IngredientItem

sealed class RefrigeratorState {
    object UnInitialize : RefrigeratorState()

    object Loading : RefrigeratorState()

    data class Success(
        val recipeList: List<IngredientItem>
    ) : RefrigeratorState()

    object Delete : RefrigeratorState()
    object Error : RefrigeratorState()
}