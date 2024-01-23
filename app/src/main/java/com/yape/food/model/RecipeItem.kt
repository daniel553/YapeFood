package com.yape.food.model

data class RecipeItem(
    val id: Long,
    val name: String = "",
    val description: String = "",
    val ingredients: List<String> = emptyList(),
    val instructions: List<String> = emptyList(),
    val url: String? = null,
    val rating: Int = 0,
)
