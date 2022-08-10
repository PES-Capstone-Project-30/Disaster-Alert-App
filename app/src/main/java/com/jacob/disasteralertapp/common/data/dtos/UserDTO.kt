package com.jacob.disasteralertapp.common.data.dtos

import com.google.firebase.firestore.IgnoreExtraProperties
import com.google.firebase.firestore.PropertyName
import com.jacob.disasteralertapp.common.models.User

@IgnoreExtraProperties
data class UserDTO(
	val id: String,
	@PropertyName("full_name") val fullName: String,
	@PropertyName("display_name") val displayName: String,
	val email: String,
	val phone: String,
	val region: String,
) {
	@Suppress("unused")
	constructor() : this(
		id = "", fullName = "", displayName = "", email = "", phone = "", region = "",
	)
}

fun UserDTO.toUser() = User(
	id = id,
	fullName = fullName,
	displayName = displayName,
	email = email,
	phone = phone,
	region = region,
)


fun User.toUserDTO() = UserDTO(
	id = id,
	fullName = fullName,
	displayName = displayName,
	email = email,
	phone = phone,
	region = region,
)
