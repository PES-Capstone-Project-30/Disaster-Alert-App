package com.jacob.disasteralertapp.common.data.dtos

import com.google.firebase.firestore.IgnoreExtraProperties
import com.google.firebase.firestore.PropertyName
import com.jacob.disasteralertapp.common.models.UserDetails

@IgnoreExtraProperties
data class UserDetailsDTO(
    val id: String,
    @PropertyName("display_name") val displayName: String,
    val email: String,
    val phone: String,
    val city: String
) {
    @Suppress("unused")
    constructor() : this(
        id = "",
        displayName = "",
        email = "",
        phone = "",
        city = ""
    )
}

fun UserDetailsDTO.toUser() = UserDetails(
    id = id,
    displayName = displayName,
    email = email,
    phone = phone,
    city = city
)

fun UserDetails.toUserDetailsDTO() = UserDetailsDTO(
    id = id,
    displayName = displayName,
    email = email,
    phone = phone,
    city = city
)
