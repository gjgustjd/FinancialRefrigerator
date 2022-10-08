package com.study.presentation.old.model.meal

import androidx.room.Embedded
import androidx.room.Relation
import com.study.presentation.old.model.recipe.RecipeItem

data class MealAndRecipeItem constructor(
    @Embedded val meal: MealItem,
    @Relation(
        parentColumn = "recipeKey",
        entityColumn = "id"
    )
    val recipe: RecipeItem
)