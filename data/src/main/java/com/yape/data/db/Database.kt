package com.yape.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.yape.data.recipe.db.RecipeDao
import com.yape.data.recipe.db.RecipeEntity

/**
 * 💡Single database to work offline
 */
@Database(entities = [RecipeEntity::class], version = 1)
@TypeConverters(Converters::class)
abstract class Database : RoomDatabase() {
    companion object {
        const val name = "yape.db"
    }

    // DAO definitions --------------------
    abstract fun recipeDao(): RecipeDao
}