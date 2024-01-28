package com.yape.data.recipe

import com.yape.data.api.ApiClientModule
import com.yape.data.fromJson
import com.yape.data.recipesJsonFile
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

//💡Remote data source tests, but it's all mocked
@HiltAndroidTest
@UninstallModules(ApiClientModule::class) //💡Prevent real api client provision
class RecipeRemoteDatasourceTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var remoteDatasource: RecipeRemoteDatasource

    private lateinit var server: MockWebServer

    @Before
    fun setUp() {
        //💡Init mock web server to work in localhost 8080
        server = MockWebServer()
        server.start(8080)
        server.enqueue(MockResponse().fromJson(recipesJsonFile)) //💡check it in assets folder
        hiltRule.inject()
    }

    @After
    fun tearDown() {
        server.shutdown()
    }

    @Test
    fun givenRecipes_whenFetchAll_thenVerifyIntegrityOfList() = runTest {
        val recipes = remoteDatasource.fetchAll()

        Assert.assertFalse(recipes.isEmpty())
        recipes.forEachIndexed { i, recipe ->
            Assert.assertTrue((i + 1).toLong() == recipe.id)
        }
    }

}