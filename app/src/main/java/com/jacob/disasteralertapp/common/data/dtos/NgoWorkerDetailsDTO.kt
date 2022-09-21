package com.jacob.disasteralertapp.common.data.dtos

import com.jacob.disasteralertapp.common.models.NgoWorkerDetails

data class NgoWorkerDetailsDTO(
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

fun NgoWorkerDetailsDTO.toNgoWorker(): NgoWorkerDetails = NgoWorkerDetails(
    id = this.id,
    displayName = this.displayName,
    email = this.email,
    phone = this.phone,
    city = this.city,
    ngoOrganization = this.ngoOrganization
)

fun NgoWorkerDetails.toNgoWorkerDTO(): NgoWorkerDetailsDTO = NgoWorkerDetailsDTO(
    id = this.id,
    displayName = this.displayName,
    email = this.email,
    phone = this.phone,
    city = this.city,
    ngoOrganization = this.ngoOrganization
)
