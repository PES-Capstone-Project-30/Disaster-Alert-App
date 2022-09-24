package com.jacob.disasteralertapp.profile

import androidx.lifecycle.ViewModel
import com.jacob.disasteralertapp.common.AuthData
import com.jacob.disasteralertapp.common.data.repositories.UsersRepository
import javax.inject.Inject

class ProfileViewModel@Inject constructor(
	private val authData: AuthData
) : ViewModel() {
    // TODO: Implement the ViewModel
	fun onLogOutBtnClicked() {
	    authData.userLogOut()
	}
}
