package com.jacob.disasteralertapp.login

import android.viewbinding.library.fragment.viewBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.jacob.disasteralertapp.R
import com.jacob.disasteralertapp.databinding.FragmentRegistrationDetailsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegistrationDetailsFragment : Fragment(R.layout.fragment_registration_details) {
	private val binding: FragmentRegistrationDetailsBinding by viewBinding()
	private val viewModel: LoginViewModel by viewModels()

}
