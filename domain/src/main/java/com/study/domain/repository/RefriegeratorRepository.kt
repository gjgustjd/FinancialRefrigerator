package com.study.domain.repository

import com.study.domain.model.IngredientItem
import com.study.domain.model.MealAndRecipeItem
import com.study.domain.model.MealItem
import com.study.domain.model.RecipeItem
import kotlinx.coroutines.flow.Flow

interface RefriegeratorRepository {
    fun getIngredients(id: Int): List<IngredientItem>
    fun insertIngredients(item: IngredientItem)
    fun deleteIngredients(item: IngredientItem)
    fun updateIngredients(item: IngredientItem)
    fun getAllIngredient(): List<IngredientItem>
    fun getAllIngredientByFlow(): Flow<List<IngredientItem>>
    fun insertRecipe(item: RecipeItem)
    fun deleteRecipe(item: RecipeItem)
    fun updateRecipe(item: RecipeItem)
    fun getAllRecipe(): List<RecipeItem>
    fun getRecipeById(id: Int): RecipeItem
    fun getRecipeByName(word: String): List<RecipeItem>
    fun getRecipeByIngredients(word: String): List<RecipeItem>
    fun getAllMeals(): List<MealItem>
    fun getMealWithId(id: Int): MealItem
    fun getAllMealsWithRecipe(): List<MealAndRecipeItem>
    fun insertMeal(id: Int)
    fun deleteMeal(item: MealItem)
    fun deleteMealWithId(mealId: Int)
}