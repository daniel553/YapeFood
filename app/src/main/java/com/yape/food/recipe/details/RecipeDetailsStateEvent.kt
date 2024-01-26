package com.yape.food.recipe.details

import com.yape.food.model.LocationItem
import com.yape.food.model.RecipeItem

/**
 * 💡State of view ->
 */
sealed interface RecipeDetailsState {
    data object Loading : RecipeDetailsState
    data object Error : RecipeDetailsState
    data class Success(val recipe: RecipeItem) : RecipeDetailsState
}

/**
 * 💡Events of ui <-
 */
sealed interface RecipeDetailsEvent {
    data object OnBack : RecipeDetailsEvent
    data class OnMapPressed(val location: LocationItem) : RecipeDetailsEvent
}

sealed interface RecipeDetailsUiEvent {
    data object Back : RecipeDetailsUiEvent
    data class OpenMap(val lat: Double, val lon: Double) : RecipeDetailsUiEvent
}