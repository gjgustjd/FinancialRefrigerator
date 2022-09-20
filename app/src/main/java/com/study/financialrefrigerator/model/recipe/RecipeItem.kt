package com.study.financialrefrigerator.model.recipe

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "recipes")
data class RecipeItem(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name:String="",
    val imageUrl:String="",
    var ingredients:String="",
    var instruction:String="",
    val time:Int=0
)