package com.study.financialrefrigerator.model

import com.study.financialrefrigerator.model.ingredient.IngredientItem
import com.study.financialrefrigerator.model.recipe.RecipeItem
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RefriegeratorRepository @Inject constructor(
    var refrigeratorDatabase: RefrigeratorDatabase
) {
    fun insertIngredients(item: IngredientItem) =
        refrigeratorDatabase.ingredientsDao().insert(item)

    fun deleteIngredients(item:IngredientItem)=
        refrigeratorDatabase.ingredientsDao().delete(item)

    fun updateIngredients(item:IngredientItem)=
        refrigeratorDatabase.ingredientsDao().update(item)

    suspend fun getAllIngredient(): List<IngredientItem> =
        refrigeratorDatabase.ingredientsDao().getAllIngredient()

    fun insertRecipe(item: RecipeItem) =
        refrigeratorDatabase.recipesDao().insert(item)

    fun deleteRecipe(item:RecipeItem)=
        refrigeratorDatabase.recipesDao().delete(item)

    fun updateRecipe(item:RecipeItem)=
        refrigeratorDatabase.recipesDao().update(item)

    suspend fun getRecipe(word:String): List<RecipeItem> =
        refrigeratorDatabase.recipesDao().getRecipe(word)
}