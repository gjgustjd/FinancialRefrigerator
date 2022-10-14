package com.study.old.old.presentation.recipe

import com.study.old.old.model.meal.MealAndRecipeItem


sealed class RecipeState {

    object UnInitialize : RecipeState()

    object Loading : RecipeState()

    data class Success(
        val recipeList: List<MealAndRecipeItem>
    ) : RecipeState()

    object Delete : RecipeState()
    object Error : RecipeState()
}