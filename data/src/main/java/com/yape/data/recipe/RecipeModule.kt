package com.yape.data.recipe

import com.yape.data.db.Database
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * 💡Binds and provides must be in separate classes
 */
@Module
@InstallIn(SingletonComponent::class)
object RecipeModuleProvider {
    @Provides
    @Singleton
    fun provideRecipeDao(db: Database) = db.recipeDao()
}

@Module
@InstallIn(SingletonComponent::class)
internal abstract class RecipeModuleBinder {
    @Binds
    @Singleton
    abstract fun bindRecipeRepository(repo: RecipeRepository): IRecipeRepository

    @Binds
    @Singleton
    abstract fun bindRecipeLocalDatasource(datasource: RecipeLocalDatasource): IRecipeLocalDatasource

    @Binds
    @Singleton
    abstract fun bindRecipeRemoteDatasource(datasource: RecipeRemoteDatasource): IRecipeRemoteDatasource
}