package com.study.financialrefrigerator.model.recipe

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recipes")
data class RecipeItem(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name:String,
    val imageUrl:String,
    val ingredients:String,
    val instruction:String,
    val time:Int
)
