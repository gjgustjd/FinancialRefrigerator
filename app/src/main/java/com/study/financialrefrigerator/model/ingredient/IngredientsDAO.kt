package com.study.financialrefrigerator.model.ingredient

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update


@Dao
interface IngredientsDAO {
    @Insert
    fun insert(item: IngredientItem)

    @Update
    fun update(item: IngredientItem)

    @Delete
    fun delete(item: IngredientItem)

    @Query("SELECT * FROM ingredients")
    suspend fun getAllIngredient(): List<IngredientItem>
}