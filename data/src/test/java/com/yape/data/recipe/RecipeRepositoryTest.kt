package com.yape.data.recipe

import com.yape.data.recipe.db.LocationEntity
import com.yape.data.recipe.db.RecipeEntity
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

internal class RecipeRepositoryTest {

    private val localDatasource: RecipeLocalDatasource = mockk(relaxed = true)
    private val remoteDatasource: RecipeRemoteDatasource = mockk(relaxed = true)

    private lateinit var repository: IRecipeRepository

    @Before
    fun setUp() {
        repository = RecipeRepository(localDatasource, remoteDatasource)
    }

    @Test
    fun `Given a recipe id When stored in local Then local datasource get recipe`() = runTest {
        val id = 1L
        val recipe = RecipeEntity(
            id, title = "title", description = "",
            location = LocationEntity(1.1, 2.2)
        )

        //Given a mock object
        coEvery { localDatasource.getById(id) } returns recipe

        repository.getById(id)

        //Verify get by id
        coVerify(atMost = 1) { localDatasource.getById(id) }

    }

    @Test
    fun `Given a no recipes id When fetch Then remote datasource get recipes`() = runTest {

        coEvery { localDatasource.getAll() } returns emptyList()
        coEvery { remoteDatasource.fetchAll() } returns emptyList()

        repository.fetch()

        coVerify(atMost = 1) { localDatasource.getAll() }
        coVerify(atMost = 1) { remoteDatasource.fetchAll() }

    }

    @Test
    fun `Given remote recipes id When fetch Then local datasource stores recipes`() = runTest {

        coEvery { localDatasource.getAll() } returns emptyList()
        coEvery { remoteDatasource.fetchAll() } returns listOf(
            RecipeEntity(
                1L, title = "title", description = "",
                location = LocationEntity(1.1, 2.2)
            )
        )

        repository.fetch()

        coVerify(atMost = 1) { localDatasource.insertAll(any()) }

    }

}