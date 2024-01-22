package com.yape.food.ui.nav

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument

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
            //TODO: Add home screen
            Text(text = "Home Screen")
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