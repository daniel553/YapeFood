package com.yape.data.recipe

import com.yape.data.asRecipeEntityList
import com.yape.data.recipe.api.IRecipeService
import com.yape.data.recipe.db.RecipeEntity
import javax.inject.Inject
import javax.inject.Singleton


interface IRecipeRemoteDatasource {
    suspend fun fetchAll(): List<RecipeEntity>
}

/**
 * 💡Remote datasource uses api services as source of truth
 */
@Singleton
class RecipeRemoteDatasource @Inject constructor(
    private val service: IRecipeService
) : IRecipeRemoteDatasource {
    override suspend fun fetchAll(): List<RecipeEntity> =
        service.getAllRecipes().getOrNull().asRecipeEntityList()
}



