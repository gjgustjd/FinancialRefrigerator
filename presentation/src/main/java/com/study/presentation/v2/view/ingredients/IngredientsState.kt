package com.study.presentation.v2.view.ingredients

import com.study.domain.model.IngredientItem


sealed class IngredientsState {

    object UnInitialize : IngredientsState()

    object Loading : IngredientsState()

    data class Success(
        val ingredientItem: IngredientItem?
    ) : IngredientsState()

    object Modify : IngredientsState()

    object Write : IngredientsState()

    object Error : IngredientsState()
}