package com.yape.data.recipe.api

import retrofit2.Response
import retrofit2.http.GET

interface RecipeApi {
    @GET("recipes") // 💡It needs to be the endpoint name
    suspend fun getAll(): Response<List<RecipeResponse>>
}