package com.yape.food.ui.nav


/**
 * 💡Define the router with a set of screens using a simple sealed interface.
 * basically the destination are simple strings used by nav controller
 */
sealed class Router(val destination: String) {

    data object HomeScreen : Router("HomeScreen")
    data object DetailScreen : Router("DetailScreen/{$ID}")
    data object MapScreen : Router("MapScreen/{$COORDINATES}")

    //💡Build a route like DetailScreen/1234
    fun buildRoute(arg: String): String {
        return destination.replace(Regex("\\{(.*)?\\}"), "").plus(arg)
    }

    companion object {
        const val ID: String = "id"
        const val COORDINATES: String= "coordinates"
    }
}