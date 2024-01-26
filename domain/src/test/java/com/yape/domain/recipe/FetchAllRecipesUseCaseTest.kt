package com.yape.domain.recipe

import com.yape.data.recipe.IRecipeRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

internal class FetchAllRecipesUseCaseTest {

    val repository: IRecipeRepository = mockk(relaxed = true)

    lateinit var subject: FetchAllRecipesUseCase

    @Before
    fun setUp() {
        subject = FetchAllRecipesUseCase(repository)
    }

    @Test
    fun `Given mock repository When invoked Then return value`() = runTest {

        coEvery { repository.fetch() } returns true

        val result = subject.invoke()

        assert(result)
    }
}