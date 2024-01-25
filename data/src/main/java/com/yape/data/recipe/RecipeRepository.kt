package com.yape.data.recipe

import com.yape.data.recipe.db.RecipeEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

/**
 * 💡Personally I like "I" as a prefix for interfaces, but commonly devs use "Impl" to differentiate
 * the interface vs its implementation
 */
interface IRecipeRepository {
    val recipes: Flow<List<RecipeEntity>>

    //💡Actions of database
    suspend fun insertToDb(recipe: RecipeEntity): Long
    suspend fun getById(id: Long): RecipeEntity
    suspend fun deleteFromDb(recipe: RecipeEntity)

    //💡Other actions from api
    suspend fun fetch(): Boolean
}

@Singleton
class RecipeRepository @Inject constructor(
    private val local: IRecipeLocalDatasource,
    private val remote: IRecipeRemoteDatasource
) : IRecipeRepository {
    override val recipes: Flow<List<RecipeEntity>>
        get() = local.recipes

    override suspend fun insertToDb(recipe: RecipeEntity): Long =
        local.insert(recipe)

    override suspend fun getById(id: Long): RecipeEntity = local.getById(id)

    override suspend fun deleteFromDb(recipe: RecipeEntity) = local.delete(recipe)

    override suspend fun fetch(): Boolean = withContext(Dispatchers.IO) {
        //💡For demo proposes, I assume that once fetched data for first time, then load local data subsequently
        var allRecipes = local.getAll()
        if (allRecipes.isEmpty()) {
            allRecipes = remote.fetchAll()
            local.insertAll(allRecipes)
        }
        allRecipes.isNotEmpty()
    }

    /**
     * 💡If long running task are required we may indicate the dispatcher ie: IO.
     * Example:
     * suspend fun doJob() = withContext(Dispatchers.IO) { task1, task2, task3.. and so on}
     */
}