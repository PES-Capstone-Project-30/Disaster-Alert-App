package com.jacob.disasteralertapp.login

import android.viewbinding.library.fragment.viewBinding
import androidx.fragment.app.Fragment
import com.jacob.disasteralertapp.R
import com.jacob.disasteralertapp.databinding.FragmentLoginBinding

class LoginFragment : Fragment(R.layout.fragment_login) {
	private val binding: FragmentLoginBinding by viewBinding()
	private lateinit var viewModel: LoginViewModel

	companion object {
		fun newInstance() = LoginFragment()
	}

}
