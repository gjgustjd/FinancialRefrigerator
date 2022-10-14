package com.study.data.repository.local

import com.study.data.database.RefrigeratorDatabase
import com.study.data.model.ingredient.IngredientEntity
import com.study.data.model.meal.MealEntity
import com.study.data.model.recipe.RecipeEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RefrigeratorLocalDataSourceImpl @Inject constructor(
    var refrigeratorDatabase: RefrigeratorDatabase
):RefrigeratorLocalDataSource {
    override suspend fun getIngredientById(id:Int) =
        refrigeratorDatabase.ingredientsDao().getIngredientById(id)

    override suspend fun insertIngredients(item: IngredientEntity) =
        refrigeratorDatabase.ingredientsDao().insert(item)

    override suspend fun deleteIngredients(item: IngredientEntity)=
        refrigeratorDatabase.ingredientsDao().delete(item)

    override suspend fun updateIngredients(item: IngredientEntity)=
        refrigeratorDatabase.ingredientsDao().update(item)

    override suspend fun getAllIngredient() =
        refrigeratorDatabase.ingredientsDao().getAllIngredient()

    override suspend fun getAllIngredientByFlow() =
        refrigeratorDatabase.ingredientsDao().getAllIngredientAsFlow()

    override suspend fun insertRecipe(item: RecipeEntity) =
        refrigeratorDatabase.recipesDao().insert(item)

    override suspend fun deleteRecipe(item: RecipeEntity)=
        refrigeratorDatabase.recipesDao().delete(item)

    override suspend fun updateRecipe(item: RecipeEntity)=
        refrigeratorDatabase.recipesDao().update(item)

    override suspend fun getAllRecipe(): List<RecipeEntity> =
        refrigeratorDatabase.recipesDao().getAllRecipe()

    override suspend fun getRecipeById(id:Int): RecipeEntity =
        refrigeratorDatabase.recipesDao().getRecipeById(id)

    override suspend fun getRecipeByName(word:String): List<RecipeEntity> =
        refrigeratorDatabase.recipesDao().getRecipeByName(word)

    override suspend fun getRecipeByIngredients(word:String): List<RecipeEntity> =
        refrigeratorDatabase.recipesDao().getRecipeByIngredients(word)

    override suspend fun getAllMeals(): List<MealEntity> =
        refrigeratorDatabase.mealsDao().getAllMeals()

    override suspend fun getMealWithId(id:Int): MealEntity =
        refrigeratorDatabase.mealsDao().getMealWithId(id)

    override suspend fun getAllMealsWithRecipe() =
        refrigeratorDatabase.mealsDao().getAllMealsWithRecipe()

    override suspend fun getAllMealsWithRecipeAsFlow() =
        refrigeratorDatabase.mealsDao().getAllMealsWithRecipeAsFlow()

    override suspend fun insertMeal(id:Int) =
        refrigeratorDatabase.mealsDao().insert(MealEntity(recipeKey = id))

    override suspend fun deleteMeal(item: MealEntity) =
        refrigeratorDatabase.mealsDao().delete(item)

    override suspend fun deleteMealWithId(mealId:Int) =
        refrigeratorDatabase.mealsDao().deleteWithId(mealId)
}