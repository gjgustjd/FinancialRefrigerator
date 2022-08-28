package com.study.financialrefrigerator.model

import com.study.financialrefrigerator.model.ingredient.IngredientItem
import com.study.financialrefrigerator.model.ingredient.IngredientsDatabase
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RefriegeratorRepository @Inject constructor(
    var ingredientsDatabase: IngredientsDatabase
) {

    fun insertIngredients(item: IngredientItem) =
        ingredientsDatabase.ingredientsDao().insert(item)

    fun deleteIngredients(item:IngredientItem)=
        ingredientsDatabase.ingredientsDao().delete(item)

    fun updateIngredients(item:IngredientItem)=
        ingredientsDatabase.ingredientsDao().update(item)

    suspend fun getAllIngredient(): List<IngredientItem> =
        ingredientsDatabase.ingredientsDao().getAllIngredient()
}