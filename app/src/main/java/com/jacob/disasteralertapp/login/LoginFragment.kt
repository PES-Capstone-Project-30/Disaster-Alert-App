package com.jacob.disasteralertapp.login

import android.viewbinding.library.fragment.viewBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.jacob.disasteralertapp.R
import com.jacob.disasteralertapp.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment(R.layout.fragment_login) {
	private val binding: FragmentLoginBinding by viewBinding()
	private val viewModel: LoginViewModel by viewModels()

}
