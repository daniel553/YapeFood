package com.yape.food.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.yape.food.recipe.RecipeListErrorView
import com.yape.food.recipe.RecipeListLoadingView
import com.yape.food.recipe.RecipeListView
import com.yape.food.ui.theme.YapeFoodTheme

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    val state = HomeState.Success(
        list = emptyList()
    )
    YapeFoodTheme {
        HomeScreen(state = state, onEvent = {})
    }
}

@Composable
fun HomeScreen(
    state: HomeState,
    onEvent: (HomeEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    //💡Depending on state a state screen will be shown
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        when (state) {
            is HomeState.Success -> {
                RecipeListView(
                    list = state.list,
                    onSelect = { onEvent(HomeEvent.OnSelected(it))},
                    modifier = Modifier.fillMaxSize()
                )
            }

            HomeState.Loading -> {
                RecipeListLoadingView(
                    modifier = Modifier.fillMaxWidth()
                )
            }

            HomeState.Error -> {
                RecipeListErrorView(
                    modifier = Modifier.fillMaxSize()
                )
            }

        }
    }
}