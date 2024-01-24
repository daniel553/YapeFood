package com.yape.data.recipe.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface RecipeDao {
    @Query("SELECT * FROM Recipe ORDER BY id")
    fun all(): Flow<List<RecipeEntity>>

    /**
     * 💡The integration with Room and coroutines manages the way to access to db
     * with <b>suspend</b> keyword to not block UI thread.
     */
    @Query("SELECT * FROM Recipe WHERE id = :id")
    suspend fun getById(id: Long): RecipeEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(recipe: RecipeEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(recipe: List<RecipeEntity>)

    @Delete
    suspend fun delete(recipe: RecipeEntity)

}