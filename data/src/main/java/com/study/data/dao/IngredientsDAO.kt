package com.study.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.study.data.model.ingredient.IngredientItem
import kotlinx.coroutines.flow.Flow


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

    @Query("SELECT * FROM ingredients")
    fun getAllIngredientAsFlow(): Flow<List<IngredientItem>>

    @Query("SELECT * FROM ingredients WHERE id = :id")
    suspend fun getIngredientById(id:Int): IngredientItem?
}