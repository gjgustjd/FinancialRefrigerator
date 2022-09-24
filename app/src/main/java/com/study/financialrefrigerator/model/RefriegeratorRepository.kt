package com.study.financialrefrigerator.model

import com.study.financialrefrigerator.model.ingredient.IngredientItem
import com.study.financialrefrigerator.model.meal.MealAndRecipeItem
import com.study.financialrefrigerator.model.meal.MealItem
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

    suspend fun getAllRecipe(): List<RecipeItem> =
        refrigeratorDatabase.recipesDao().getAllRecipe()

    suspend fun getRecipeById(id:Int): RecipeItem =
        refrigeratorDatabase.recipesDao().getRecipeById(id)

    suspend fun getRecipeByName(word:String): List<RecipeItem> =
        refrigeratorDatabase.recipesDao().getRecipeByName(word)

    suspend fun getRecipeByIngredients(word:String): List<RecipeItem> =
        refrigeratorDatabase.recipesDao().getRecipeByIngredients(word)

    suspend fun getAllMeals(): List<MealItem> =
        refrigeratorDatabase.mealsDao().getAllMeals()

    suspend fun getAllMealsWithRecipe() =
        refrigeratorDatabase.mealsDao().getAllMealsWithRecipe()

    suspend fun insertMeal(id:Int) =
        refrigeratorDatabase.mealsDao().insert(MealItem(recipeKey = id))
    suspend fun deleteMeal(item:MealItem) =
        refrigeratorDatabase.mealsDao().delete(item)
}