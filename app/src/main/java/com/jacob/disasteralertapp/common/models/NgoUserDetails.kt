package com.jacob.disasteralertapp.common.models

data class NgoUserDetails(
    val id: String,
    val displayName: String,
    val email: String,
    val phone: String,
    val city: String,
    val ngoOrganization: String
)
