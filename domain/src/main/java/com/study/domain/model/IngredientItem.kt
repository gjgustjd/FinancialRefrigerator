package com.study.domain.model


data class IngredientItem(
    val id: Int = 0,
    val name:String,
    val description:String?,
    val quantity:Int,
    val unit:String,
    val shelf_life:Int
)
