package com.study.financialrefrigerator.model

import androidx.room.Database
import androidx.room.RoomDatabase
import com.study.financialrefrigerator.model.ingredient.IngredientItem
import com.study.financialrefrigerator.model.ingredient.IngredientsDAO
import com.study.financialrefrigerator.model.recipe.RecipeItem
import com.study.financialrefrigerator.model.recipe.RecipesDAO

@Database(entities = [IngredientItem::class,RecipeItem::class], version = 4)
abstract class RefrigeratorDatabase : RoomDatabase() {
    abstract fun ingredientsDao(): IngredientsDAO
    abstract fun recipesDao(): RecipesDAO
}

