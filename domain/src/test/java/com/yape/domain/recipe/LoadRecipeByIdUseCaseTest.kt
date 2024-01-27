package com.yape.domain.recipe

import com.yape.data.recipe.IRecipeRepository
import com.yape.data.recipe.db.LocationEntity
import com.yape.data.recipe.db.RecipeEntity
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test

internal class LoadRecipeByIdUseCaseTest {

    val repository: IRecipeRepository = mockk(relaxed = true)

    lateinit var subject: LoadRecipeByIdUseCase

    @Before
    fun setUp() {
        subject = LoadRecipeByIdUseCase(repository)
    }

    @Test
    fun `Given mock repository When invoked Then return value`() = runTest {
        val recipe = RecipeEntity(1L, "title", "", location = LocationEntity(1.1, 2.2))
        coEvery { repository.getById(any()) } returns recipe

        val result = subject.invoke(1L)

        Assert.assertEquals(result.id, recipe.id)
        Assert.assertEquals(result.title, recipe.title)
        Assert.assertEquals(result.description, recipe.description)
    }
}