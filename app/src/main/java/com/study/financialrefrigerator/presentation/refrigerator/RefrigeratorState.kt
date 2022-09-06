package com.study.financialrefrigerator.presentation.refrigerator

import com.study.financialrefrigerator.presentation.recipe.RecipeEntity
import com.study.financialrefrigerator.presentation.recipe.RecipeState

sealed class RefrigeratorState {
    object UnInitialize :RefrigeratorState()

    object Loading : RefrigeratorState()

    data class Success(
        val recipeList: List<RefrigeratorEntity>
    ) : RefrigeratorState()

    object Delete : RefrigeratorState()
    object Error : RefrigeratorState()
}