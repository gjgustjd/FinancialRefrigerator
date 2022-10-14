package com.study.old.old.presentation.refrigerator

import com.study.old.old.model.ingredient.IngredientItem

sealed class RefrigeratorState {
    object UnInitialize : RefrigeratorState()

    object Loading : RefrigeratorState()

    data class Success(
        val recipeList: List<IngredientItem>
    ) : RefrigeratorState()

    object Delete : RefrigeratorState()
    object Error : RefrigeratorState()
}