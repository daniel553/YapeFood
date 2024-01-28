package com.yape.data.recipe.api

import com.yape.data.api.ApiClientModule
import com.yape.data.fromJson
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
@UninstallModules(ApiClientModule::class) //💡Prevent real api client provision
class RecipeServiceTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var recipeService: RecipeService

    private lateinit var server: MockWebServer

    @Before
    fun setUp() {
        //💡Init mock web server to work in localhost 8080
        server = MockWebServer()
        server.start(8080)
        hiltRule.inject()
    }

    @After
    fun tearDown() {
        server.shutdown()
    }

    @Test
    fun givenValidResponse_whenGetAllRecipes_thenVerifyIsSuccessAndData() = runTest {
        //💡enqueue response for a valid json
        server.enqueue(MockResponse().fromJson("recipes.json"))
        val result = recipeService.getAllRecipes()

        Assert.assertTrue(result.isSuccess)
        val recipes = result.getOrNull()
        Assert.assertNotNull(recipes)
    }

    @Test
    fun givenErrorResponse_whenGetAllRecipes_thenVerifyIsError() = runTest {
        //💡enqueue response for invalid response
        server.enqueue(MockResponse().setBody(""))
        val result = recipeService.getAllRecipes()

        Assert.assertTrue(result.isFailure)
        val recipes = result.getOrNull()
        Assert.assertNull(recipes)
    }

}