package com.study.domain.model

data class MealAndRecipeItem constructor(
    val meal: MealItem,
    val recipe: RecipeItem
) : DomainItem() {
    override fun getIdentifier(): String {
        return meal.toString()
    }
}