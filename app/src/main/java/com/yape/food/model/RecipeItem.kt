package com.yape.food.model

import com.yape.domain.model.Recipe

data class RecipeItem(
    val id: Long,
    val name: String = "",
    val description: String = "",
    val ingredients: List<String> = emptyList(),
    val instructions: List<String> = emptyList(),
    val url: String? = null,
    val rating: Int = 0,
    val location: LocationItem? = null
)

data class LocationItem(
    val lat: Double,
    val lon: Double,
)

// Transformation --------
fun List<Recipe>.toRecipeItemList(): List<RecipeItem> = this.map { recipe -> recipe.toRecipeItem() }

fun Recipe.toRecipeItem(): RecipeItem = RecipeItem(
    id = this.id,
    name = this.title,
    description = this.description,
    ingredients = this.ingredients,
    instructions = instructions,
    url = this.url,
    rating = this.rating,
    location = LocationItem(this.location.lat, this.location.lon)
)
