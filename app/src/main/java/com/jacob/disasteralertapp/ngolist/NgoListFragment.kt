package com.jacob.disasteralertapp.ngolist

import android.viewbinding.library.fragment.viewBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.jacob.disasteralertapp.R
import com.jacob.disasteralertapp.databinding.NgoListFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NgoListFragment : Fragment(R.layout.ngo_list_fragment) {
    private val binding: NgoListFragmentBinding by viewBinding()
    private val viewModel: NgoListViewModel by viewModels()
}
