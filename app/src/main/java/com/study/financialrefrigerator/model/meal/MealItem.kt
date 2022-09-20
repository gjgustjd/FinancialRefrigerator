package com.study.financialrefrigerator.model.meal

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.study.financialrefrigerator.model.recipe.RecipeItem

@Entity(tableName = "meals")
data class MealItem @Ignore constructor(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val recipeKey:Int,
    @Ignore val recipeItem: RecipeItem?=null
) {
    constructor(id: Int = 0, recipeKey: Int) : this(id, recipeKey, null)
}