package com.study.data.repository.local

import com.study.data.model.ingredient.IngredientEntity
import com.study.data.model.meal.MealAndRecipeEntity
import com.study.data.model.meal.MealEntity
import com.study.data.model.recipe.RecipeEntity
import kotlinx.coroutines.flow.Flow

interface RefrigeratorLocalDataSource {
    suspend fun getIngredientById(id: Int): IngredientEntity?
    suspend fun insertIngredients(item: IngredientEntity)
    suspend fun deleteIngredients(item: IngredientEntity)
    suspend fun updateIngredients(item: IngredientEntity)
    suspend fun getAllIngredient(): List<IngredientEntity>
    suspend fun getAllIngredientByFlow(): Flow<List<IngredientEntity>>
    suspend fun insertRecipe(item: RecipeEntity)
    suspend fun deleteRecipe(item: RecipeEntity)
    suspend fun updateRecipe(item: RecipeEntity)
    suspend fun getAllRecipe(): List<RecipeEntity>
    suspend fun getRecipeById(id: Int): RecipeEntity
    suspend fun getRecipeByName(word: String): List<RecipeEntity>
    suspend fun getRecipeByIngredients(word: String): List<RecipeEntity>
    suspend fun getAllMeals(): List<MealEntity>
    suspend fun getMealWithId(id: Int): MealEntity
    suspend fun getAllMealsWithRecipe(): List<MealAndRecipeEntity>
    suspend fun getAllMealsWithRecipeAsFlow():Flow<List<MealAndRecipeEntity>>
    suspend fun insertMeal(id: Int)
    suspend fun deleteMeal(item: MealEntity)
    suspend fun deleteMealWithId(mealId: Int)
}