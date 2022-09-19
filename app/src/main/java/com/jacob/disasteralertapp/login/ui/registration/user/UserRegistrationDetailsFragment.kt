package com.jacob.disasteralertapp.login.ui.registration.user

import android.os.Bundle
import android.view.View
import android.viewbinding.library.fragment.viewBinding
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.jacob.disasteralertapp.R
import com.jacob.disasteralertapp.databinding.UserRegistrationDetailsFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserRegistrationDetailsFragment : Fragment(R.layout.user_registration_details_fragment) {
    private val binding: UserRegistrationDetailsFragmentBinding by viewBinding()
    private val viewModel: UserRegistrationDetailsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.doneBtn.setOnClickListener {
            findNavController().navigate(UserRegistrationDetailsFragmentDirections.toNgoListFragment())
        }

        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_list_item_1,
            viewModel.getActiveLocations()
        )
        val autoCompleteTextView = binding.selectLocation.editText as? AutoCompleteTextView
        autoCompleteTextView?.setAdapter(adapter)

        autoCompleteTextView?.setOnItemClickListener { adapterView, _, position, _ ->
            val item = adapterView.getItemAtPosition(position) as String
            viewModel.setSelectedLocation(item)
            binding.doneBtn.isEnabled = true
        }
    }
}
