package com.study.financialrefrigerator.presentation.refrigerator.ingredients

import com.study.financialrefrigerator.model.ingredient.IngredientItem

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