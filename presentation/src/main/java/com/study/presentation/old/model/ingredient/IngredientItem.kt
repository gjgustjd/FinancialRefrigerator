package com.study.presentation.old.model.ingredient

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ingredients")
data class IngredientItem(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name:String,
    val description:String?,
    val quantity:Int,
    val unit:String,
    val shelf_life:Int
)
