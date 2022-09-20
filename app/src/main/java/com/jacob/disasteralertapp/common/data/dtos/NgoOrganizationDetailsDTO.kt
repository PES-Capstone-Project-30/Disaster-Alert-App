package com.jacob.disasteralertapp.common.data.dtos

import com.jacob.disasteralertapp.common.models.NgoOrganizationDetails

data class NgoOrganizationDetailsDTO(
    val id: String,
    val name: String
) {
    @Suppress("unused")
    constructor() : this(
        id = "",
        name = ""
    )
}

fun NgoOrganizationDetailsDTO.toNgoOrganizationDetails() = NgoOrganizationDetails(
    id = id,
    name = name
)
