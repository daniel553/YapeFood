package com.yape.food.home

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.yape.domain.recipe.FetchAllRecipesUseCase
import com.yape.domain.recipe.SubscribeToRecipesUseCase
import io.mockk.mockk
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

//💡
@RunWith(AndroidJUnit4::class)
internal class HomeViewModelTest {

    private val fetchAllRecipesUseCase: FetchAllRecipesUseCase = mockk()

    private val subscribeToRecipesUseCase: SubscribeToRecipesUseCase = mockk()

    private lateinit var viewModel: HomeViewModel

    @Before
    fun setUp() {
        viewModel = HomeViewModel(fetchAllRecipesUseCase, subscribeToRecipesUseCase)
    }

    @Test
    fun givenViewModel_whenInitialUiState_thenLoadingStateIsSet() {
        val value = viewModel.uiState.value

        Assert.assertTrue(value == HomeState.Loading)
    }

    //TODO: Test events, probably using Turbine to and verify flows values

}