package com.yape.data.recipe.db

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Recipe")
data class RecipeEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long = 0L,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "description")
    val description: String,
    @ColumnInfo(name = "ingredients")
    val ingredients: List<String> = emptyList(),
    @ColumnInfo(name = "instructions")
    val instructions: List<String> = emptyList(),
    @ColumnInfo(name = "url")
    val url: String? = null,
    @ColumnInfo(name = "rating")
    val rating: Int = 0,

    @Embedded
    val location: LocationEntity
)

data class LocationEntity(
    val lat: Double,
    val lon: Double
)