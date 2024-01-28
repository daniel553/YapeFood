package com.yape.food.map

sealed interface MapState {
    data object Loading : MapState
    data class Success(val lat: Double, val lon: Double) : MapState
}

sealed interface MapEvent {
    data class OnGoPressed(val lat: Double, val lon: Double) : MapEvent
    data object OnBackPressed : MapEvent
}

sealed interface MapUiEvent {
    data class LaunchExternalMap(val lat: Double, val lon: Double) : MapUiEvent
    data object Back : MapUiEvent
}