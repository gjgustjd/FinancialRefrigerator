package com.study.presentation.old.presentation.recipe

import com.study.presentation.old.model.meal.MealAndRecipeItem

sealed class RecipeState {

    object UnInitialize : RecipeState()

    object Loading : RecipeState()

    data class Success(
        val recipeList: List<MealAndRecipeItem>
    ) : RecipeState()

    object Delete : RecipeState()
    object Error : RecipeState()
}