package com.study.financialrefrigerator.presentation.recipe

sealed class RecipeState {

    object UnInitialize : RecipeState()

    object Loading : RecipeState()

    data class Success(
        val recipeList: List<RecipeEntity>
    ) : RecipeState()

    object Delete : RecipeState()
    object Error : RecipeState()
}