package com.yape.food.ui.nav

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.yape.food.home.HomeScreen
import com.yape.food.home.HomeUiEvent
import com.yape.food.home.HomeViewModel
import com.yape.food.recipe.details.RecipeDetailsScreen
import com.yape.food.recipe.details.RecipeDetailsUiEvent
import com.yape.food.recipe.details.RecipeDetailsViewModel
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

// 💡Main navigation will hold the list of recipes
@Composable
fun MainNavigation(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Router.HomeScreen.destination,
    ) {
        composable(
            route = Router.HomeScreen.destination
        ) {
            val viewModel: HomeViewModel = hiltViewModel()
            val state by viewModel.uiState.collectAsStateWithLifecycle()
            // 💡Launch effect on view model change, but
            LaunchedEffect(viewModel) {
                viewModel.uiEvent.onEach { event ->
                    when (event) {
                        is HomeUiEvent.DetailsScreen -> navController.navigate(
                            Router.DetailScreen.buildRoute(
                                event.id.toString()
                            )
                        )
                    }
                }.stateIn(this)
            }
            HomeScreen(state = state, onEvent = viewModel::onEvent)
        }

        composable(
            route = Router.DetailScreen.destination,
            arguments = listOf(navArgument(Router.ID) { type = NavType.LongType }),
        ) {
            val viewModel: RecipeDetailsViewModel = hiltViewModel()
            val state by viewModel.uiState.collectAsStateWithLifecycle()
            // 💡Launch effect on view model change, but
            LaunchedEffect(viewModel) {
                viewModel.uiEvent.onEach { event ->
                    when (event) {
                        is RecipeDetailsUiEvent.OpenMap -> navController.navigate(
                            Router.MapScreen.buildRoute("${event.lat},${event.lon}")
                        )

                        RecipeDetailsUiEvent.Back -> navController.popBackStack()
                    }
                }.stateIn(this)
            }
            RecipeDetailsScreen(state = state, onEvent = viewModel::onEvent)
        }

        composable(
            route = Router.MapScreen.destination,
            arguments = listOf(navArgument(Router.ID) { type = NavType.LongType }),
        ) { stack ->
            //TODO: Add map screen
            val id = stack.arguments?.getLong(Router.ID)
            Text(text = "Map screen $id")
        }
    }
}