package com.yape.data.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


object Converters {
    @TypeConverter
    fun fromStringArrayList(value: List<String>): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun toStringArrayList(value: String): List<String> {
        return try {
            Gson().fromJson<List<String>>(value) //using extension function
        } catch (e: Exception) {
            arrayListOf()
        }
    }
}

private inline fun <reified T> Gson.fromJson(json: String) =
    fromJson<T>(json, object : TypeToken<T>() {}.type)