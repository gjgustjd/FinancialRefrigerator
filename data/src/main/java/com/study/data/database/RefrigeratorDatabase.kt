package com.study.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.study.data.model.ingredient.IngredientItem
import com.study.data.dao.IngredientsDAO
import com.study.data.dao.MealsDAO
import com.study.data.model.meal.MealItem
import com.study.data.model.recipe.RecipeItem
import com.study.data.dao.RecipesDAO

@Database(entities = [IngredientItem::class, RecipeItem::class, MealItem::class], version = 7)
abstract class RefrigeratorDatabase : RoomDatabase() {
    abstract fun ingredientsDao(): IngredientsDAO
    abstract fun recipesDao(): RecipesDAO
    abstract fun mealsDao(): MealsDAO
}

