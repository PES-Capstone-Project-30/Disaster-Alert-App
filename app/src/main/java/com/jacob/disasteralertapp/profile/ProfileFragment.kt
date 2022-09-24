package com.jacob.disasteralertapp.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.viewbinding.library.fragment.viewBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.jacob.disasteralertapp.R
import com.jacob.disasteralertapp.databinding.ProfileFragmentBinding
import com.jacob.disasteralertapp.databinding.UserRegistrationDetailsFragmentBinding
import com.jacob.disasteralertapp.login.ui.registration.user.UserRegistrationDetailsViewModel

class ProfileFragment : Fragment(R.layout.profile_fragment) {
    private val binding: ProfileFragmentBinding by viewBinding()
    private val viewModel: ProfileViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.logOutButton.setOnClickListener {

            findNavController().navigate(
                ProfileFragmentDirections.actionProfileToLoginFragment()
            )
            viewModel.onLogOutBtnClicked()

        }
    }
}
