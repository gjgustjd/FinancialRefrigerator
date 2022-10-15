package com.study.presentation.v2.view.refrigerator

import com.study.domain.model.IngredientItem


sealed class RefrigeratorState {
    object UnInitialize : RefrigeratorState()

    object Loading : RefrigeratorState()

    data class Success(
        val recipeList: List<IngredientItem>
    ) : RefrigeratorState()

    object Delete : RefrigeratorState()
    object Error : RefrigeratorState()
}