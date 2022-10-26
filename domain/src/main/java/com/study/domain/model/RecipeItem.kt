package com.study.domain.model

data class RecipeItem(
    val id: Int = 0,
    val name: String = "",
    val imageUrl: String = "",
    var ingredients: String = "",
    var instruction: String = "",
    val time: Int = 0
) : DomainItem() {
    override fun getIdentifier(): String {
        return id.toString()
    }
}