package com.jacob.disasteralertapp.login

import android.viewbinding.library.fragment.viewBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.jacob.disasteralertapp.R
import com.jacob.disasteralertapp.databinding.NgoRegistrationDetailsFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NgoRegistrationDetailsFragment : Fragment(R.layout.ngo_registration_details_fragment) {
	private val binding: NgoRegistrationDetailsFragmentBinding by viewBinding()
	private val viewModel: NgoRegistrationDetailsViewModel by viewModels()
}
