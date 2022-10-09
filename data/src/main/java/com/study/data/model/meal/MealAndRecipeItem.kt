package com.study.data.model.meal

import androidx.room.Embedded
import androidx.room.Relation
import com.study.data.model.recipe.RecipeItem

data class MealAndRecipeItem constructor(
    @Embedded val meal: MealItem,
    @Relation(
        parentColumn = "recipeKey",
        entityColumn = "id"
    )
    val recipe: RecipeItem
)