package com.yape.food.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yape.domain.model.Recipe
import com.yape.domain.recipe.FetchAllRecipesUseCase
import com.yape.domain.recipe.SubscribeToRecipesUseCase
import com.yape.food.model.contains
import com.yape.food.model.toRecipeItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.merge
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

    private val searchFlow = MutableStateFlow("")

    init {
        viewModelScope.launch {
            subscribe()
            fetchAllRecipesUseCase()
        }
    }

    //💡Subscribe to flows for use case and local search
    private suspend fun subscribe() {
        //💡to "combine" both flows
        merge(
            searchFlow,
            subscribeToRecipesUseCase(),
        ).onStart {
            Log.d("HomeViewModel", "Starting collecting recipes")
        }.onEach { result ->
            when (result) { //💡determine what kind of flow is
                is String -> updateFilter(result)
                is List<*> -> updateList(result)
            }
        }.catch { error ->
            Log.d("HomeViewModel", "error: $error")
            _uiState.update {
                HomeState.Error
            }
        }.stateIn(viewModelScope)
    }

    fun onEvent(event: HomeEvent) {
        viewModelScope.launch {
            when (event) {
                is HomeEvent.OnSelected -> _uiEvent.emit(HomeUiEvent.DetailsScreen(event.selected.id))
                is HomeEvent.OnSearch -> handleSearch(event.query)
            }
        }
    }

    private fun handleSearch(queryInput: String) {
        val query = queryInput.trim()
        viewModelScope.launch {
            searchFlow.emit(query)
        }
    }

    private fun updateFilter(result: String) {
        _uiState.update { state ->
            when (state) {
                is HomeState.Success -> {
                    state.copy(
                        query = result,
                        list = state.all.filter { item ->
                            result.isEmpty() || item.contains(result)
                        }
                    )
                }

                else -> {
                    _uiState.value
                }
            }
        }
    }

    private fun updateList(result: List<*>) {
        val recipes = result.map { (it as Recipe).toRecipeItem() }
        _uiState.update {
            HomeState.Success(list = recipes, all = recipes)
        }
    }

}
