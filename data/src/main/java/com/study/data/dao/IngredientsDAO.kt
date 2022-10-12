package com.study.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.study.data.model.ingredient.IngredientEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface IngredientsDAO {
    @Insert
    fun insert(item: IngredientEntity)

    @Update
    fun update(item: IngredientEntity)

    @Delete
    fun delete(item: IngredientEntity)

    @Query("SELECT * FROM ingredients")
    suspend fun getAllIngredient(): List<IngredientEntity>

    @Query("SELECT * FROM ingredients")
    fun getAllIngredientAsFlow(): Flow<List<IngredientEntity>>

    @Query("SELECT * FROM ingredients WHERE id = :id")
    suspend fun getIngredientById(id:Int): IngredientEntity?
}