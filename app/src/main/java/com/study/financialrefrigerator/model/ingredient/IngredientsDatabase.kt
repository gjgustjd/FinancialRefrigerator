package com.study.financialrefrigerator.model.ingredient

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [IngredientItem::class], version = 3)
abstract class IngredientsDatabase : RoomDatabase() {
    abstract fun ingredientsDao(): IngredientsDAO
}

