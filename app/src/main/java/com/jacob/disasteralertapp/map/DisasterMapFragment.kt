package com.jacob.disasteralertapp.map

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.jacob.disasteralertapp.R

class DisasterMapFragment : Fragment() {

	companion object {
		fun newInstance() = DisasterMapFragment()
	}

	private lateinit var viewModel: DisasterMapViewModel

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?,
	): View? {
		return inflater.inflate(R.layout.disaster_map_fragment, container, false)
	}

	override fun onActivityCreated(savedInstanceState: Bundle?) {
		super.onActivityCreated(savedInstanceState)
		viewModel = ViewModelProvider(this).get(DisasterMapViewModel::class.java)
		// TODO: Use the ViewModel
	}

}