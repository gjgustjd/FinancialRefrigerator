package com.study.financialrefrigerator.model.meal

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.study.financialrefrigerator.model.recipe.RecipeItem
import kotlinx.coroutines.flow.Flow


@Dao
interface MealsDAO {
    @Insert
    fun insert(item: MealItem)

    @Update
    fun update(item: MealItem)

    @Delete
    fun delete(item: MealItem)

    @Query("DELETE FROM meals WHERE id = :mealId")
    fun deleteWithId(mealId:Int)

    @Query("SELECT * FROM meals")
    suspend fun getAllMeals(): List<MealItem>

    @Query("SELECT * FROM meals WHERE id = :id")
    suspend fun getMealWithId(id:Int): MealItem

    @Query("SELECT * FROM meals")
    suspend fun getAllMealsWithRecipe(): List<MealAndRecipeItem>

    @Query("SELECT * FROM meals")
    fun getAllMealsWithRecipeAsFlow(): Flow<List<MealAndRecipeItem>>
}