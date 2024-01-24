package com.yape.food.ui.nav

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.yape.food.home.HomeScreen
import com.yape.food.home.HomeViewModel

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
            HomeScreen(state = state, onEvent = {})
        }

        composable(
            route = Router.DetailScreen.destination,
            arguments = listOf(navArgument(Router.ID) { type = NavType.LongType }),
        ) { stack ->
            //TODO: Add Details screen
            val id = stack.arguments?.getLong(Router.ID)
            Text(text = "Details screen $id")
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