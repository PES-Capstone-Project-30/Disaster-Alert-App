package com.jacob.disasteralertapp.login.ui.registration.user

import androidx.lifecycle.ViewModel
import com.jacob.disasteralertapp.common.AuthData
import com.jacob.disasteralertapp.common.data.repositories.UsersRepository
import com.jacob.disasteralertapp.common.models.User
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UserRegistrationDetailsViewModel @Inject constructor(
	private val usersRepository: UsersRepository,
	private val authData: AuthData,
) : ViewModel() {
	private lateinit var selectedLocation: String

	fun getActiveLocations() = listOf("A", "B", "C", "D")

	fun setSelectedLocation(item: String) {
		selectedLocation = item
	}

	fun onDoneClicked() {
		val loggedInUser = authData.getLoggedInUser()

		val user = User(
			id = loggedInUser.uid,
			displayName = loggedInUser.displayName ?: "",
			region = selectedLocation,
			email = loggedInUser.email ?: "",
			phone = loggedInUser.phoneNumber ?: "",
		)

		usersRepository.addUser(user)
	}
}