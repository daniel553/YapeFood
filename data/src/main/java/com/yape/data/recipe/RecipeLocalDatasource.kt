package com.yape.data.recipe

import com.yape.data.recipe.db.RecipeDao
import com.yape.data.recipe.db.RecipeEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton


interface IRecipeLocalDatasource {
    val recipes: Flow<List<RecipeEntity>>

    suspend fun getAll(): List<RecipeEntity>
    suspend fun insert(recipe: RecipeEntity): Long
    suspend fun insertAll(recipes: List<RecipeEntity>)
    suspend fun getById(id: Long): RecipeEntity
    suspend fun delete(recipe: RecipeEntity)
}

/**
 * 💡Use a datasource as a source of truth on local data.
 */
@Singleton
class RecipeLocalDatasource @Inject constructor(
    private val dao: RecipeDao
) : IRecipeLocalDatasource {

    /**
     * 💡Smooth integration of Flow with DAOs of Room
     */
    override val recipes: Flow<List<RecipeEntity>>
        get() = dao.all()

    override suspend fun getAll(): List<RecipeEntity> = dao.getAll()
    override suspend fun insert(recipe: RecipeEntity): Long = dao.insert(recipe)

    override suspend fun insertAll(recipes: List<RecipeEntity>) = dao.insertAll(recipes)

    override suspend fun getById(id: Long): RecipeEntity = dao.getById(id)

    override suspend fun delete(recipe: RecipeEntity) = dao.delete(recipe)

}

