package com.yape.food

import com.yape.food.model.LocationItem
import com.yape.food.model.RecipeItem

fun getSampleRecipeList(count: Int) = (0 until count).map {
    RecipeItem(
        id = it.toLong(),
        name = "name$it",
        description = "description$it",
        ingredients = listOf("1", "2"),
        instructions = listOf("1", "2"),
        url = "",
        location = LocationItem(1.1, 2.2)
    )
}