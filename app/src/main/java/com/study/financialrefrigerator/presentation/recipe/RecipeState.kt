package com.study.financialrefrigerator.presentation.recipe

import com.study.financialrefrigerator.model.recipe.RecipeItem

sealed class RecipeState {

    object UnInitialize : RecipeState()

    object Loading : RecipeState()

    data class Success(
        val recipeList: List<RecipeItem>
    ) : RecipeState()

    object Delete : RecipeState()
    object Error : RecipeState()
}