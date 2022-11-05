package com.study.domain.repository

import com.study.domain.model.*
import com.study.domain.model.agricultureAPI.SeasonlyAgricultureIAPItem
import io.reactivex.Single
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

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

    suspend fun getCrawlWebLinkItemFlow(keyword: String,endNum: Int, coroutineNum: Int): Flow<WebLinkItem>
    fun getAgriculturalProductData(productName:String): Single<Response<SeasonlyAgricultureIAPItem>>
}