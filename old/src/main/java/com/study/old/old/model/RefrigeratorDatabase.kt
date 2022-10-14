package com.study.old.old.model

import androidx.room.Database
import androidx.room.RoomDatabase
import com.study.old.old.model.ingredient.IngredientItem
import com.study.old.old.model.ingredient.IngredientsDAO
import com.study.old.old.model.meal.MealsDAO
import com.study.old.old.model.meal.MealItem
import com.study.old.old.model.recipe.RecipeItem
import com.study.old.old.model.recipe.RecipesDAO

@Database(entities = [IngredientItem::class, RecipeItem::class, MealItem::class], version = 7)
abstract class RefrigeratorDatabase : RoomDatabase() {
    abstract fun ingredientsDao(): IngredientsDAO
    abstract fun recipesDao(): RecipesDAO
    abstract fun mealsDao(): MealsDAO
}

