package com.jacob.disasteralertapp.home

import android.viewbinding.library.fragment.viewBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.jacob.disasteralertapp.R
import com.jacob.disasteralertapp.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {
	private val binding: FragmentHomeBinding by viewBinding()
	private val viewModel: HomeViewModel by viewModels()
}
