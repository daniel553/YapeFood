package com.yape.food.recipe

import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.filter
import androidx.compose.ui.test.hasClickAction
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollToIndex
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.yape.food.getSampleRecipeList
import com.yape.food.model.RecipeItem
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
internal class RecipeListViewTest {

    @get: Rule
    val composeTestRule = createComposeRule()

    private val sampleList = getSampleRecipeList(5)

    private var selected: RecipeItem? = null
    val onSelect: (RecipeItem) -> Unit = { selected = it }

    @Test
    fun givenNonEmptyList_whenShowList_thenShowItems() {
        composeTestRule.setContent {
            RecipeListView(list = sampleList, onSelect = {})
        }

        composeTestRule.onNodeWithTag(RecipeListViewTag.ListTag.name).assertIsDisplayed()
        composeTestRule.onNodeWithTag(RecipeListViewTag.HeaderTag.name).assertIsDisplayed()
    }

    @Test
    fun givenEmptyList_whenShowList_thenShowItems() {
        composeTestRule.setContent {
            RecipeListView(list = emptyList(), onSelect = {})
        }

        composeTestRule.onNodeWithTag(RecipeListViewTag.EmptyTag.name).assertIsDisplayed()
    }

    @Test
    fun givenList_whenScrolled_thenMustDisplayAll() {
        composeTestRule.setContent {
            RecipeListView(list = sampleList, onSelect = { onSelect(it) })
        }

        //💡Verify scrollable list to bottom one
        composeTestRule
            .onNodeWithTag(RecipeListViewTag.ListTag.name)
            .performScrollToIndex(sampleList.size)

        //Verify count
        composeTestRule.onAllNodesWithTag(RecipeListViewTag.ListItem.name)
            .assertCountEquals(5)
    }

    @Test
    fun givenRecipe_whenSelect_thenOnSelectEventTriggered() {
        composeTestRule.setContent {
            RecipeListView(list = sampleList, onSelect = { onSelect(it) })
        }

        //💡Verify the click on list item
        composeTestRule
            .onAllNodesWithTag(RecipeListViewTag.ListItem.name)
            .filter(hasClickAction())
            .onFirst()
            .performClick()

        Assert.assertEquals(sampleList.first(), selected)

    }

    //TODO: test filter from search bar.

}