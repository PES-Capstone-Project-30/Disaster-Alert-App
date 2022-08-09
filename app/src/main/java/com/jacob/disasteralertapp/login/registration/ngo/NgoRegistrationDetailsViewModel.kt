package com.jacob.disasteralertapp.login.registration.ngo

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NgoRegistrationDetailsViewModel @Inject constructor() : ViewModel() {
	fun getRegisteredNgos(): List<String> {
		return listOf("a", "b", "c", "d", "e", "f", "g", "h", "i", "Other")
	}
}
