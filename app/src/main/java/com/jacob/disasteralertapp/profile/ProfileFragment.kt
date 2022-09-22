package com.jacob.disasteralertapp.profile

import android.os.Bundle
import android.view.View
import android.viewbinding.library.fragment.viewBinding
import androidx.fragment.app.Fragment
import com.jacob.disasteralertapp.R
import com.jacob.disasteralertapp.common.workers.UpdateLocationWorker
import com.jacob.disasteralertapp.databinding.ProfileFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment(R.layout.profile_fragment) {
    private val binding: ProfileFragmentBinding by viewBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.settingsButton.setOnClickListener {
            UpdateLocationWorker.createWorkerRequest(requireContext())
        }
    }
}
