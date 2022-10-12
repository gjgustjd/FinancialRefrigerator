package com.study.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.study.data.model.recipe.RecipeEntity

@Dao
interface RecipesDAO {
    @Insert
    fun insert(item: RecipeEntity)

    @Update
    fun update(item: RecipeEntity)

    @Delete
    fun delete(item: RecipeEntity)

    @Query("SELECT * FROM recipes")
    suspend fun getAllRecipe(): List<RecipeEntity>

    @Query("SELECT * FROM recipes WHERE id = :id")
    suspend fun getRecipeById(id: Int): RecipeEntity

    @Query("SELECT * FROM recipes WHERE name LIKE :word")
    suspend fun getRecipeByName(word: String): List<RecipeEntity>

    @Query("SELECT * FROM recipes WHERE ingredients LIKE :word")
    suspend fun getRecipeByIngredients(word: String): List<RecipeEntity>
}