package com.study.data.repository

import com.study.data.mapper.*
import com.study.data.repository.local.RefrigeratorLocalDataSource
import com.study.domain.model.IngredientItem
import com.study.domain.model.MealAndRecipeItem
import com.study.domain.model.MealItem
import com.study.domain.model.RecipeItem
import com.study.domain.repository.RefrigeratorRepository
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RefrigeratorRepositoryImpl @Inject constructor(
    val localDataSource: RefrigeratorLocalDataSource
): RefrigeratorRepository {
    override suspend fun getIngredientById(id: Int) =
        localDataSource.getIngredientById(id)?.toIngredientItem()

    override suspend fun insertIngredients(item: IngredientItem) =
        localDataSource.insertIngredients(item.toIngredientEntity())

    override suspend fun deleteIngredients(item: IngredientItem) =
        localDataSource.deleteIngredients(item.toIngredientEntity())

    override suspend fun updateIngredients(item: IngredientItem) =
        localDataSource.updateIngredients(item.toIngredientEntity())

    override suspend fun getAllIngredient() =
        localDataSource.getAllIngredient().mapTo(arrayListOf()) { it.toIngredientItem() }.toList()

    override suspend fun getAllIngredientByFlow() =
        localDataSource.getAllIngredientByFlow()
            .map {
                it.mapTo(arrayListOf()) {
                    it.toIngredientItem()
                }.toList()
            }

    override suspend fun insertRecipe(item: RecipeItem) =
        localDataSource.insertRecipe(item.toRecipeEntity())

    override suspend fun deleteRecipe(item: RecipeItem) =
        localDataSource.deleteRecipe(item.toRecipeEntity())

    override suspend fun updateRecipe(item: RecipeItem) =
        localDataSource.updateRecipe(item.toRecipeEntity())

    override suspend fun getAllRecipe(): List<RecipeItem> =
        localDataSource.getAllRecipe().mapTo(arrayListOf()) { it.toRecipeItem() }.toList()

    override suspend fun getRecipeById(id: Int): RecipeItem =
        localDataSource.getRecipeById(id).toRecipeItem()

    override suspend fun getRecipeByName(word: String): List<RecipeItem> =
        localDataSource.getRecipeByName(word).mapTo(arrayListOf()) { it.toRecipeItem() }.toList()

    override suspend fun getRecipeByIngredients(word: String): List<RecipeItem> =
        localDataSource.getRecipeByIngredients(word).mapTo(arrayListOf()) { it.toRecipeItem() }.toList()

    override suspend fun getAllMeals(): List<MealItem> =
        localDataSource.getAllMeals().mapTo(arrayListOf()) { it.toMealItem() }.toList()

    override suspend fun getMealWithId(id: Int): MealItem =
        localDataSource.getMealWithId(id).toMealItem()

    override suspend fun getAllMealsWithRecipe(): List<MealAndRecipeItem> =
        localDataSource.getAllMealsWithRecipe().mapTo(arrayListOf()) { it.toMealAndRecipeItem() }.toList()

    override suspend fun getAllMealsWithRecipeAsFlow() =
        localDataSource.getAllMealsWithRecipeAsFlow()
            .map {
                it.mapTo(arrayListOf()) {
                    it.toMealAndRecipeItem()
                }.toList()
            }

    override suspend fun insertMeal(id: Int) =
        localDataSource.insertMeal(id)

    override suspend fun deleteMeal(item: MealItem) =
        localDataSource.deleteMeal(item.toMealEntity())

    override suspend fun deleteMealWithId(mealId: Int) =
        localDataSource.deleteMealWithId(mealId)

}