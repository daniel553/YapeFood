package com.yape.data

import android.content.Context
import androidx.room.Room
import com.yape.data.api.ApiUrl
import com.yape.data.db.Database
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

/**
 * 💡DI for instrumented tests
 */
@Module
@InstallIn(ActivityComponent::class)
internal object TestModule {

    @Provides
    @Named("test-yape")
    fun provideTestDatabase(@ApplicationContext context: Context) = Room
        .inMemoryDatabaseBuilder(context = context, Database::class.java)
        .allowMainThreadQueries() //💡to execute queries in the main thread
        .build()
}

@Module
@InstallIn(SingletonComponent::class)
object TestApiClientModule {
    private const val BASE_URL = "http://localhost:8080/"

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