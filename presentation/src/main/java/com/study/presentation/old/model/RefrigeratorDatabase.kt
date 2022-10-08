package com.study.presentation.old.model

import androidx.room.Database
import androidx.room.RoomDatabase
import com.study.presentation.old.model.ingredient.IngredientItem
import com.study.presentation.old.model.ingredient.IngredientsDAO
import com.study.presentation.old.model.meal.MealsDAO
import com.study.presentation.old.model.meal.MealItem
import com.study.presentation.old.model.recipe.RecipeItem
import com.study.presentation.old.model.recipe.RecipesDAO

@Database(entities = [IngredientItem::class, RecipeItem::class, MealItem::class], version = 7)
abstract class RefrigeratorDatabase : RoomDatabase() {
    abstract fun ingredientsDao(): IngredientsDAO
    abstract fun recipesDao(): RecipesDAO
    abstract fun mealsDao(): MealsDAO
}

