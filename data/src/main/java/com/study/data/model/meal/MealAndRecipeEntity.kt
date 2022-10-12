package com.study.data.model.meal

import androidx.room.Embedded
import androidx.room.Relation
import com.study.data.model.recipe.RecipeEntity

data class MealAndRecipeEntity constructor(
    @Embedded val meal: MealEntity,
    @Relation(
        parentColumn = "recipeKey",
        entityColumn = "id"
    )
    val recipe: RecipeEntity
)