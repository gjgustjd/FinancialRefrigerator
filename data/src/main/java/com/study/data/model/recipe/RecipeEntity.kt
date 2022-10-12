package com.study.data.model.recipe

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recipes")
data class RecipeEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name:String="",
    val imageUrl:String="",
    var ingredients:String="",
    var instruction:String="",
    val time:Int=0
)