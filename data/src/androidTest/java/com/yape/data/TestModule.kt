package com.yape.data

import android.content.Context
import androidx.room.Room
import com.yape.data.db.Database
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Named

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