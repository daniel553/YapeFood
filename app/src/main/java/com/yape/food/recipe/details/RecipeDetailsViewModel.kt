package com.yape.food.recipe.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yape.domain.recipe.LoadRecipeByIdUseCase
import com.yape.food.model.toRecipeItem
import com.yape.food.ui.nav.Router
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

//💡Recipe details view model will load the recipe by id
@HiltViewModel
class RecipeDetailsViewModel @Inject constructor(
    //💡It's good to use saved state handler when passing args to view model
    private val savedStateHandle: SavedStateHandle,
    //💡Space to inject the use cases.
    private val loadRecipeByIdUseCase: LoadRecipeByIdUseCase
) : ViewModel() {
    //💡Get the id from the arguments on saved state
    private val id = checkNotNull(savedStateHandle.get<Long>(Router.ID))

    private val _uiState: MutableStateFlow<RecipeDetailsState> =
        MutableStateFlow(RecipeDetailsState.Loading)
    val uiState = _uiState.asStateFlow()

    private val _uiEvent: MutableSharedFlow<RecipeDetailsUiEvent> = MutableSharedFlow()
    val uiEvent = _uiEvent.asSharedFlow()

    init {
        loadRecipe()
    }

    private fun loadRecipe() {
        viewModelScope.launch {
            loadRecipeByIdUseCase(id).let { recipe ->
                _uiState.update { RecipeDetailsState.Success(recipe.toRecipeItem()) }
            }
        }
    }

    fun onEvent(event: RecipeDetailsEvent) {
        viewModelScope.launch {
            when (event) {
                is RecipeDetailsEvent.OnMapPressed -> _uiEvent.emit(
                    RecipeDetailsUiEvent.OpenMap(
                        lat = event.location.lat,
                        lon = event.location.lon
                    )
                )

                RecipeDetailsEvent.OnBack -> _uiEvent.emit(RecipeDetailsUiEvent.Back)
            }
        }
    }
}