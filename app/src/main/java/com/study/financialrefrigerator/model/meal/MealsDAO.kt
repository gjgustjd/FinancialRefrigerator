package com.study.financialrefrigerator.model.meal

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update


@Dao
interface MealsDAO {
    @Insert
    fun insert(item: MealItem)

    @Update
    fun update(item: MealItem)

    @Delete
    fun delete(item: MealItem)

    @Query("SELECT * FROM meals")
    suspend fun getAllMeals(): List<MealItem>
}