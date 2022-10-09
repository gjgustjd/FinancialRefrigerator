package com.study.data.model.meal

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "meals")
data class MealItem (
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val recipeKey:Int
)