package com.yape.food.home

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.yape.food.getSampleRecipeList
import com.yape.food.ui.theme.YapeFoodTheme
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

//💡Show home different states test
@RunWith(AndroidJUnit4::class)
internal class HomeScreenTest {

    @get: Rule
    val composeTestRule = createComposeRule()

    private lateinit var state: HomeState

    @Test
    fun whenSuccessState_thenShowListView() {
        state = HomeState.Success(list = getSampleRecipeList(5))
        setState()
        verifyExists(HomeScreenTestTag.RecipeListViewTag.name)
    }

    @Test
    fun whenLoadingState_thenShowLoadingView() {
        state = HomeState.Loading
        setState()
        verifyExists(HomeScreenTestTag.RecipeListLoadingViewTag.name)
    }

    @Test
    fun whenErrorState_thenShowErrorView() {
        state = HomeState.Error
        setState()
        verifyExists(HomeScreenTestTag.RecipeListErrorViewTag.name)
    }

    //💡Why not @Before annotation? set content view should be called once
    private fun setState() {
        composeTestRule.setContent {
            YapeFoodTheme {
                HomeScreen(state = state, onEvent = {})
            }
        }
    }

    private fun verifyExists(tag: String) = composeTestRule.onNodeWithTag(tag).assertExists()

}