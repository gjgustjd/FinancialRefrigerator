package com.study.presentation.v2.view.recipe

import com.study.domain.model.MealAndRecipeItem


sealed class RecipeState {

    object UnInitialize : RecipeState()

    object Loading : RecipeState()

    data class Success(
        val recipeList: List<MealAndRecipeItem>
    ) : RecipeState()

    object Delete : RecipeState()
    object Error : RecipeState()
}