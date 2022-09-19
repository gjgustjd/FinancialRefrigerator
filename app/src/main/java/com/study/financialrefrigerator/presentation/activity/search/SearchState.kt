package com.study.financialrefrigerator.presentation.activity.search

import com.study.financialrefrigerator.model.meal.MealItem

sealed class SearchState {
    object UnInitialize : SearchState()

    object Loading : SearchState()

    data class Success(
        val recipeList: List<MealItem>
    ) : SearchState()

    object Delete : SearchState()
    object Error : SearchState()
}