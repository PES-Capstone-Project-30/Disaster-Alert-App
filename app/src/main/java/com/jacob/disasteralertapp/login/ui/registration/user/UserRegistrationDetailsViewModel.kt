package com.jacob.disasteralertapp.login.ui.registration.user

import androidx.lifecycle.ViewModel
import com.jacob.disasteralertapp.common.AuthData
import com.jacob.disasteralertapp.common.data.repositories.UsersRepository
import com.jacob.disasteralertapp.common.models.UserDetails
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UserRegistrationDetailsViewModel @Inject constructor(
    private val usersRepository: UsersRepository,
    private val authData: AuthData
) : ViewModel() {
    fun getActiveLocations() = listOf("A", "B", "C", "D")

    fun onDoneClicked(selectedLocation: String) {
        val loggedInUser = authData.getLoggedInFirebaseUser()

        val userDetails = UserDetails(
            id = loggedInUser.uid,
            displayName = loggedInUser.displayName ?: "",
            city = selectedLocation,
            email = loggedInUser.email ?: "",
            phone = loggedInUser.phoneNumber ?: ""
        )

        usersRepository.addUser(userDetails)
        authData.userLoggedIn(userDetails)
    }
}
