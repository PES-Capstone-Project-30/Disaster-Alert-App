package com.jacob.disasteralertapp.common.data.dtos

import com.jacob.disasteralertapp.common.models.LocationData
import com.jacob.disasteralertapp.common.models.UserType

data class LocationDataDTO(
    val lat: Double,
    val lon: Double,
    val name: String,
    val type: String
) {
    @Suppress("unused")
    constructor() : this(
        lat = 0.0,
        lon = 0.0,
        name = "",
        type = ""
    )
}

fun LocationDataDTO.toLocationData(): LocationData = LocationData(
    latitude = lat,
    longitude = lon,
    name = name,
    type = UserType.valueOf(type)
)
