package com.study.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.study.data.model.recipe.RecipeItem

@Dao
interface RecipesDAO {
    @Insert
    fun insert(item: RecipeItem)

    @Update
    fun update(item: RecipeItem)

    @Delete
    fun delete(item: RecipeItem)

    @Query("SELECT * FROM recipes")
    suspend fun getAllRecipe(): List<RecipeItem>

    @Query("SELECT * FROM recipes WHERE id = :id")
    suspend fun getRecipeById(id: Int): RecipeItem

    @Query("SELECT * FROM recipes WHERE name LIKE :word")
    suspend fun getRecipeByName(word: String): List<RecipeItem>

    @Query("SELECT * FROM recipes WHERE ingredients LIKE :word")
    suspend fun getRecipeByIngredients(word: String): List<RecipeItem>
}