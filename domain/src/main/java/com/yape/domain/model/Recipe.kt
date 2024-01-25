package com.yape.domain.model

import com.yape.data.recipe.db.LocationEntity
import com.yape.data.recipe.db.RecipeEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

data class Recipe(
    val id: Long = 0L,
    val title: String,
    val description: String,
    val ingredients: List<String> = emptyList(),
    val instructions: List<String> = emptyList(),
    val url: String? = null,
    val rating: Int = 0,
    val location: Location
)


// Transformations ---------
fun Flow<List<RecipeEntity>>.toFlowRecipeList(): Flow<List<Recipe>> =
    this.map { list -> list.map { it.toRecipe() } }

fun RecipeEntity.toRecipe(): Recipe = Recipe(
    id = this.id,
    title = this.title,
    description = this.description,
    ingredients = this.ingredients,
    instructions = this.instructions,
    url = this.url,
    rating = this.rating,
    location = this.location.toLocation()
)
