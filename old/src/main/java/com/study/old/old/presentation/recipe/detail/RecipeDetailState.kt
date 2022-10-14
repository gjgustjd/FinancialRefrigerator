package com.study.old.old.presentation.recipe.detail

import com.study.old.old.model.recipe.RecipeItem

sealed class RecipeDetailState {

    object UnInitialize : RecipeDetailState()

    object Loading : RecipeDetailState()

    data class Success(
        val recipeDetail: RecipeItem
    ) : RecipeDetailState()

    object Delete : RecipeDetailState()
    object Error : RecipeDetailState()
}