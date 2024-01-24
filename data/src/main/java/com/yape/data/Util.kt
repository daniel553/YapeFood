package com.yape.data

import com.yape.data.recipe.api.RecipeResponse
import com.yape.data.recipe.db.LocationEntity
import com.yape.data.recipe.db.RecipeEntity

fun List<RecipeResponse>?.asRecipeEntityList(): List<RecipeEntity> {
    return this?.map { recipeResponse -> recipeResponse.toRecipeEntity() } ?: emptyList()
}

fun RecipeResponse.toRecipeEntity() = RecipeEntity(
    id = this.id,
    title = this.title,
    description = this.description,
    ingredients = this.ingredients,
    instructions = this.instructions,
    url = this.url,
    rating = this.rating,
    location = LocationEntity(lat = this.location.lat, lon = this.location.lon)
)