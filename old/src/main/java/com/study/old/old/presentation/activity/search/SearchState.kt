package com.study.old.old.presentation.activity.search

import com.study.old.old.model.meal.MealItem

sealed class SearchState {
    object UnInitialize : SearchState()

    object Loading : SearchState()

    data class Success(
        val recipeList: List<MealItem>
    ) : SearchState()

    object Delete : SearchState()
    object Error : SearchState()
}