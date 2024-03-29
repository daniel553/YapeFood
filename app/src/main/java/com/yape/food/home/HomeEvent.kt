package com.yape.food.home

import com.yape.food.model.RecipeItem

/**
 * 💡This auxiliary file is used to define the state and the events associated to view,
 * the intention is to seal to modifications and visualize all cases covered
 */
sealed interface HomeState {
    data object Loading : HomeState
    data object Error : HomeState
    data class Success(
        val list: List<RecipeItem>,
        val query: String = "",
        val all: List<RecipeItem> = emptyList()
    ) : HomeState
}

sealed interface HomeEvent {
    data class OnSelected(val selected: RecipeItem) : HomeEvent
    data class OnSearch(val query: String) : HomeEvent
}

sealed interface HomeUiEvent {
    data class DetailsScreen(val id: Long) : HomeUiEvent
}