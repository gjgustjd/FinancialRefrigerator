package com.study.domain.model


data class MealItem(
    val id: Int = 0,
    val recipeKey: Int
) : DomainItem() {
    override fun getIdentifier(): String {
        return id.toString()
    }
}