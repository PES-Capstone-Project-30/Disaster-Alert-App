package com.jacob.disasteralertapp.common.data.dtos

import com.jacob.disasteralertapp.common.models.LocationData
import com.jacob.disasteralertapp.common.models.UserType

data class LocationDataDTO(
    val lat: Double,
    val lng: Double,
    val name: String,
    val type: String
) {
    @Suppress("unused")
    constructor() : this(
        lat = 0.0,
        lng = 0.0,
        name = "",
        type = ""
    )
}

fun LocationDataDTO.toLocationData(): LocationData = LocationData(
    latitude = lat,
    longitude = lng,
    name = name,
    type = UserType.valueOf(type)
)
