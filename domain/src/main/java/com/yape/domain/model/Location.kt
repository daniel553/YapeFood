package com.yape.domain.model

import com.yape.data.recipe.db.LocationEntity

data class Location(val lat: Double, val lon: Double)

// Transformations ----
fun LocationEntity.toLocation(): Location = Location(
    lat = this.lat,
    lon = this.lon
)