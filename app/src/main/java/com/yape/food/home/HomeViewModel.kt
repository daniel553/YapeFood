package com.yape.food.home

import androidx.lifecycle.ViewModel
import com.yape.food.model.RecipeItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

/**
 * 💡The home view model will use main flows, one for the up going events and other for
 * the state changes.
 */
@HiltViewModel
class HomeViewModel @Inject constructor(
    //💡Space to inject the use cases.
) : ViewModel() {

    private val _uiState: MutableStateFlow<HomeState> =
        MutableStateFlow(HomeState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        //TODO: remove
        _uiState.update { state ->
            HomeState.Success(
                list = (0..3).map { RecipeItem(it.toLong()) }
            )
        }
    }

}