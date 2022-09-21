package com.jacob.disasteralertapp.common.models

class UserDetails(
    id: String,
    displayName: String,
    email: String,
    phone: String,
    city: String
) : BaseUser(
    id = id,
    displayName = displayName,
    email = email,
    phone = phone,
    city = city
)
