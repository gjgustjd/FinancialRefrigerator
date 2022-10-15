package com.study.presentation.v2.view.recipeDetail

import com.study.domain.model.RecipeItem


sealed class RecipeDetailState {

    object UnInitialize : RecipeDetailState()

    object Loading : RecipeDetailState()

    data class Success(
        val recipeDetail: RecipeItem
    ) : RecipeDetailState()

    object Delete : RecipeDetailState()
    object Error : RecipeDetailState()
}