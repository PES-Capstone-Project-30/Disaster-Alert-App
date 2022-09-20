package com.jacob.disasteralertapp.common.data.dtos

import com.jacob.disasteralertapp.common.models.NgoUserDetails

data class NgoUserDetailsDTO(
    val id: String,
    val displayName: String,
    val email: String,
    val phone: String,
    val city: String,
    val ngoOrganization: String
) {
    @Suppress("unused")
    constructor() : this(
        id = "",
        displayName = "",
        email = "",
        phone = "",
        city = "",
        ngoOrganization = ""
    )
}

fun NgoUserDetailsDTO.toNgoUser(): NgoUserDetails {
    return NgoUserDetails(
        id = this.id,
        displayName = this.displayName,
        email = this.email,
        phone = this.phone,
        city = this.city,
        ngoOrganization = this.ngoOrganization
    )
}
