package com.yape.data.api

import com.yape.data.recipe.api.IRecipeService
import com.yape.data.recipe.api.RecipeApi
import com.yape.data.recipe.api.RecipeService
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Qualifier
import javax.inject.Singleton


/**
 * General API client module, installed on singleton component
 */
@Module
@InstallIn(SingletonComponent::class)
object ApiClientModule {
    private const val BASE_URL = "https://demo5922620.mockable.io/"

    @Singleton
    @Provides
    fun provideRetrofit(@ApiUrl apiUrl: String): Retrofit {
        return Retrofit
            .Builder()
            .baseUrl(apiUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    @ApiUrl
    fun provideApiURL(): String = BASE_URL
}

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class ApiUrl

/**
 * Retrofit
 */
@Module
@InstallIn(SingletonComponent::class)
object ApiServiceModule {
    @Provides
    @Singleton
    fun provideRecipeApi(retrofit: Retrofit): RecipeApi = retrofit.create(RecipeApi::class.java)
}


/**
 * Service module.
 */
@Module
@InstallIn(SingletonComponent::class)
interface ServiceModule {
    @Binds
    fun bindRecipeService(recipeService: RecipeService): IRecipeService

}