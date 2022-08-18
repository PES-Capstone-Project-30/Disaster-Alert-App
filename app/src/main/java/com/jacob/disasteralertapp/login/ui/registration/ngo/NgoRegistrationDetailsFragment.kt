package com.jacob.disasteralertapp.login.ui.registration.ngo

import android.os.Bundle
import android.view.View
import android.viewbinding.library.fragment.viewBinding
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.jacob.disasteralertapp.R
import com.jacob.disasteralertapp.databinding.NgoRegistrationDetailsFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NgoRegistrationDetailsFragment : Fragment(R.layout.ngo_registration_details_fragment) {
	private val binding: NgoRegistrationDetailsFragmentBinding by viewBinding()
	private val viewModel: NgoRegistrationDetailsViewModel by viewModels()

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, viewModel.getRegisteredNgos())
		val autoCompleteTextView = binding.selectNgo.editText as? AutoCompleteTextView
		autoCompleteTextView?.setAdapter(adapter)

		autoCompleteTextView?.setOnItemClickListener { adapterView, _, position, _ ->
			val item = adapterView.getItemAtPosition(position) as String
			binding.doneBtn.isEnabled = true
			if (item == "Other") {
				binding.newNgoDetailsSection.visibility = View.VISIBLE
			} else {
				binding.newNgoDetailsSection.visibility = View.GONE
			}
		}
	}
}
