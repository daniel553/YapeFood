package com.yape.data.recipe.api

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

interface IRecipeService {
    /**
     * Fetch all recipes
     */
    suspend fun getAllRecipes(): Result<List<RecipeResponse>?>

}

@Singleton
class RecipeService @Inject constructor(
    private val api: RecipeApi
) : IRecipeService {

    override suspend fun getAllRecipes(): Result<List<RecipeResponse>?> {
        return withContext(Dispatchers.IO) {
            val recipes = runCatching {
                api.getAll()
            }.getOrNull()

            if (recipes != null) {
                if (recipes.isSuccessful) {
                    Result.success(recipes.body()!!)
                } else {
                    Result.failure(Exception("Can not get all Recipes"))
                }
            } else {
                Result.failure(Exception("Can not get all Recipes from network"))
            }
        }
    }

}