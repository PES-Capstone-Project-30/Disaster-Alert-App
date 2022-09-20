package com.jacob.disasteralertapp.common.data.dtos

import com.jacob.disasteralertapp.common.models.LocationData
import com.jacob.disasteralertapp.common.models.UserType

data class LocationDataDto(
    val lat: Double,
    val lon: Double,
    val name: String,
    val type: String
)

fun LocationDataDto.toLocationData(): LocationData = LocationData(
    latitude = lat,
    longitude = lon,
    name = name,
    type = UserType.valueOf(type)
)
