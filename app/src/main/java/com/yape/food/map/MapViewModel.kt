package com.yape.food.map

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yape.food.ui.nav.Router
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    //💡To get coordinates
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val coordinates = savedStateHandle.get<String>(Router.COORDINATES) ?: "0,0"
    private var lat: Double = 0.0
    private var lon: Double = 0.0

    private val _uiState: MutableStateFlow<MapState> = MutableStateFlow(MapState.Loading)
    val uiState = _uiState.asStateFlow()

    private val _uiEvent: MutableSharedFlow<MapUiEvent> = MutableSharedFlow()
    val uiEvent = _uiEvent.asSharedFlow()

    init {
        val split = coordinates.split(",", limit = 2)
        lat = split.first().toDouble()
        lon = split.last().toDouble()
        launchMap()
    }

    private fun launchMap() {
        viewModelScope.launch {
            delay(500)
            _uiState.update { MapState.Success(lat, lon) }
        }
    }

    fun onEvent(event: MapEvent) {
        viewModelScope.launch {
            when (event) {
                MapEvent.OnBackPressed -> _uiEvent.emit(MapUiEvent.Back)
                is MapEvent.OnGoPressed -> _uiEvent.emit(MapUiEvent.LaunchExternalMap(event.lat, event.lon))
            }
        }
    }

}