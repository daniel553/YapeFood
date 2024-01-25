package com.yape.data.recipe.api

/*💡This is a special model for api and network calls
 * Expecting to have the following format
 * <code>
 * [
 * {
 *   "id": 1,
 *   "title": "Title",
 *   "description": "Description",
 *   "ingredients": [""],
 *   "instructions": [""],
 *   "url": "",
 *   "rating": 5,
 *   "location": {
 *    "lat": 19.3906591,
 *    "lon": -99.308768
 *   },
 * ...]
 *  </code>
 */
data class RecipeResponse(
    val id: Long = 0L,
    val title: String,
    val description: String,
    val ingredients: List<String>,
    val instructions: List<String>,
    val url: String,
    val rating: Int,
    val location: LocationResponse
)

data class LocationResponse(
    val lat: Double,
    val lon: Double
)
