package com.study.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.study.data.model.meal.MealAndRecipeEntity
import com.study.data.model.meal.MealEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface MealsDAO {
    @Insert
    fun insert(item: MealEntity)

    @Update
    fun update(item: MealEntity)

    @Delete
    fun delete(item: MealEntity)

    @Query("DELETE FROM meals WHERE id = :mealId")
    fun deleteWithId(mealId:Int)

    @Query("SELECT * FROM meals")
    suspend fun getAllMeals(): List<MealEntity>

    @Query("SELECT * FROM meals WHERE id = :id")
    suspend fun getMealWithId(id:Int): MealEntity

    @Query("SELECT * FROM meals")
    suspend fun getAllMealsWithRecipe(): List<MealAndRecipeEntity>

    @Query("SELECT * FROM meals")
    fun getAllMealsWithRecipeAsFlow(): Flow<List<MealAndRecipeEntity>>
}