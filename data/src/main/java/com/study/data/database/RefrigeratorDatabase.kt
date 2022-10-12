package com.study.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.study.data.model.ingredient.IngredientEntity
import com.study.data.dao.IngredientsDAO
import com.study.data.dao.MealsDAO
import com.study.data.model.meal.MealEntity
import com.study.data.model.recipe.RecipeEntity
import com.study.data.dao.RecipesDAO

@Database(entities = [IngredientEntity::class, RecipeEntity::class, MealEntity::class], version = 7)
abstract class RefrigeratorDatabase : RoomDatabase() {
    abstract fun ingredientsDao(): IngredientsDAO
    abstract fun recipesDao(): RecipesDAO
    abstract fun mealsDao(): MealsDAO
}

