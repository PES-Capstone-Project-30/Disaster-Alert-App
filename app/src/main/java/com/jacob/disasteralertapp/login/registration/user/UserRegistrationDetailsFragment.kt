package com.jacob.disasteralertapp.login.registration.user

import android.viewbinding.library.fragment.viewBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.jacob.disasteralertapp.R
import com.jacob.disasteralertapp.databinding.UserRegistrationDetailsFragmentBinding
import com.jacob.disasteralertapp.login.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserRegistrationDetailsFragment : Fragment(R.layout.user_registration_details_fragment) {
	private val binding: UserRegistrationDetailsFragmentBinding by viewBinding()
	private val viewModel: LoginViewModel by viewModels()

}
