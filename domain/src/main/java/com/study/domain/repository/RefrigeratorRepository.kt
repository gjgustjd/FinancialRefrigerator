package com.study.domain.repository

import com.study.domain.model.IngredientItem
import com.study.domain.model.MealAndRecipeItem
import com.study.domain.model.MealItem
import com.study.domain.model.RecipeItem
import kotlinx.coroutines.flow.Flow

interface RefrigeratorRepository {
    suspend fun getIngredientById(id: Int): IngredientItem?
    suspend fun insertIngredients(item: IngredientItem)
    suspend fun deleteIngredients(item: IngredientItem)
    suspend fun updateIngredients(item: IngredientItem)
    suspend fun getAllIngredient(): List<IngredientItem>
    suspend fun getAllIngredientByFlow(): Flow<List<IngredientItem>>
    suspend fun insertRecipe(item: RecipeItem)
    suspend fun deleteRecipe(item: RecipeItem)
    suspend fun updateRecipe(item: RecipeItem)
    suspend fun getAllRecipe(): List<RecipeItem>
    suspend fun getRecipeById(id: Int): RecipeItem
    suspend fun getRecipeByName(word: String): List<RecipeItem>
    suspend fun getRecipeByIngredients(word: String): List<RecipeItem>
    suspend fun getAllMeals(): List<MealItem>
    suspend fun getMealWithId(id: Int): MealItem
    suspend fun getAllMealsWithRecipe(): List<MealAndRecipeItem>
    suspend fun getAllMealsWithRecipeAsFlow():Flow<List<MealAndRecipeItem>>
    suspend fun insertMeal(id: Int)
    suspend fun deleteMeal(item: MealItem)
    suspend fun deleteMealWithId(mealId: Int)
}