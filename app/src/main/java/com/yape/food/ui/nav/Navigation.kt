package com.yape.food.ui.nav

import android.content.Intent
import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.yape.food.home.HomeScreen
import com.yape.food.home.HomeUiEvent
import com.yape.food.home.HomeViewModel
import com.yape.food.map.MapUiEvent
import com.yape.food.map.MapViewModel
import com.yape.food.map.MapViewScreen
import com.yape.food.recipe.details.RecipeDetailsScreen
import com.yape.food.recipe.details.RecipeDetailsUiEvent
import com.yape.food.recipe.details.RecipeDetailsViewModel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn

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
            arguments = listOf(navArgument(Router.COORDINATES) { type = NavType.StringType }),
        ) {

            val viewModel: MapViewModel = hiltViewModel()
            val state by viewModel.uiState.collectAsStateWithLifecycle()
            val scope = rememberCoroutineScope()
            val context = LocalContext.current

            // 💡Launch effect on view model change, but
            LaunchedEffect(viewModel) {
                viewModel.uiEvent.onEach { event ->
                    when (event) {
                        MapUiEvent.Back -> navController.popBackStack()
                        is MapUiEvent.LaunchExternalMap -> coroutineScope {
                            val gmmIntentUri =
                                Uri.parse("geo:${event.lat},${event.lon}?z=10&q=restaurants")
                            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                            mapIntent.setPackage("com.google.android.apps.maps")
                            context.startActivity(mapIntent)
                        }
                    }
                }.stateIn(this)
            }
            MapViewScreen(state = state, onEvent = viewModel::onEvent)
        }
    }
}