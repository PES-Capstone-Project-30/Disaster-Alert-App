package com.jacob.disasteralertapp.login.ui.registration.user

import androidx.lifecycle.ViewModel
import com.jacob.disasteralertapp.common.data.repositories.UsersRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UserRegistrationDetailsViewModel @Inject constructor(
	private val usersRepository: UsersRepository
) : ViewModel() {
	private lateinit var selectedLocation: String

	fun getActiveLocations() = listOf("A", "B", "C", "D")

	fun setSelectedLocation(item: String) {
		selectedLocation = item
	}

	fun onDoneClicked() {
		TODO("Not yet implemented")
	}
}
