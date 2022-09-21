package com.study.financialrefrigerator.model.meal

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.study.financialrefrigerator.model.recipe.RecipeItem

@Entity(tableName = "meals")
data class MealItem (
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val recipeKey:Int
)