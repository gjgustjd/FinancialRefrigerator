package com.study.presentation.v2.view.searchRecipe

import com.study.domain.model.MealItem

sealed class SearchState {
    object UnInitialize : SearchState()

    object Loading : SearchState()

    data class Success(
        val recipeList: List<MealItem>
    ) : SearchState()

    object Delete : SearchState()
    object Error : SearchState()
}