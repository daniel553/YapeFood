package com.yape.data.recipe.db

import androidx.test.filters.SmallTest
import com.yape.data.db.Database
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.io.IOException
import javax.inject.Inject

@HiltAndroidTest
@SmallTest
internal class RecipeDaoTest {
    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var db: Database

    //💡Some devs like to use subject as variable name of that is going to be tested in the suit
    // personally, I don't like it, I prefer to use a name similar to regular convention
    private lateinit var subject: RecipeDao

    @Before
    fun setUp() {
        hiltRule.inject()
        subject = db.recipeDao()
    }

    //💡db can throw an io exception when attempting to close
    @After
    @Throws(IOException::class)
    fun tearDown() {
        db.close()
    }

    @Test
    fun writeRecipe_thenIdIsReturned() = runTest {
        val recipe = RecipeEntity(
            id = 0L, title = "title", description = "",
            location = LocationEntity(1.1, 2.2)
        )

        val result = subject.insert(recipe)

        Assert.assertNotEquals(recipe.id, result)
    }

    @Test
    fun writeRecipe_thenGetRecipeById() = runTest {
        val recipe = RecipeEntity(
            id = 0L, title = "title", description = "",
            location = LocationEntity(1.1, 2.2)
        )

        val id = subject.insert(recipe)

        val result = subject.getById(id)

        Assert.assertEquals(id, result.id)
        Assert.assertEquals(recipe.title, result.title)
        Assert.assertEquals(recipe.description, result.description)
    }

    //TODO: Add more cases

}