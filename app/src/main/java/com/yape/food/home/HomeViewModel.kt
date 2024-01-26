package com.yape.food.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yape.domain.recipe.FetchAllRecipesUseCase
import com.yape.domain.recipe.SubscribeToRecipesUseCase
import com.yape.food.model.toRecipeItemList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * 💡The home view model will use main flows, one for the up going events and other for
 * the state changes.
 */
@HiltViewModel
class HomeViewModel @Inject constructor(
    //💡Space to inject the use cases.
    private val fetchAllRecipesUseCase: FetchAllRecipesUseCase,
    private val subscribeToRecipesUseCase: SubscribeToRecipesUseCase
) : ViewModel() {

    private val _uiState: MutableStateFlow<HomeState> =
        MutableStateFlow(HomeState.Loading)
    val uiState = _uiState.asStateFlow()

    private val _uiEvent: MutableSharedFlow<HomeUiEvent> = MutableSharedFlow()
    val uiEvent = _uiEvent.asSharedFlow()

    init {
        viewModelScope.launch {
            subscribe()
            fetchAllRecipesUseCase()
        }
    }

    suspend fun subscribe() {
        subscribeToRecipesUseCase()
            .onStart {
                Log.d("HomeViewModel", "Starting collecting recipes")
            }
            .onEach { recipes ->
                if (recipes.isNotEmpty()) {
                    _uiState.update {
                        HomeState.Success(list = recipes.toRecipeItemList())
                    }
                }
            }.catch {
                _uiState.update {
                    HomeState.Error
                }
            }.stateIn(viewModelScope)
    }

    fun onEvent(event: HomeEvent) {
        viewModelScope.launch {
            when (event) {
                is HomeEvent.OnSelected -> _uiEvent.emit(HomeUiEvent.DetailsScreen(event.selected.id))
            }
        }
    }

}
