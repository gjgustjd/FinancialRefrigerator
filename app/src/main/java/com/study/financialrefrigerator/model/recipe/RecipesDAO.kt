package com.study.financialrefrigerator.model.recipe

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface RecipesDAO {
    @Insert
    fun insert(item: RecipeItem)

    @Update
    fun update(item: RecipeItem)

    @Delete
    fun delete(item: RecipeItem)

    @Query("SELECT * FROM recipes WHERE name LIKE :word")
    suspend fun getRecipe(word: String): List<RecipeItem>
}