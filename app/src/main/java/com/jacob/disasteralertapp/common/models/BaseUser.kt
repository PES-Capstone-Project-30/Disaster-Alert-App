package com.jacob.disasteralertapp.common.models

sealed class BaseUser(
    val id: String,
    val displayName: String,
    val email: String,
    val phone: String,
    val city: String
)
