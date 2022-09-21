package com.jacob.disasteralertapp.common.models

class NgoWorkerDetails(
    id: String,
    displayName: String,
    email: String,
    phone: String,
    city: String,
    val ngoOrganization: String
) : BaseUser(
    id = id,
    displayName = displayName,
    email = email,
    phone = phone,
    city = city
)
