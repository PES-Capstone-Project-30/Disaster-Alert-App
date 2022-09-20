package com.jacob.disasteralertapp.map

import android.os.Bundle
import android.view.View
import android.viewbinding.library.fragment.viewBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.jacob.disasteralertapp.R
import com.jacob.disasteralertapp.databinding.DisasterMapFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DisasterMapFragment : Fragment(R.layout.disaster_map_fragment) {
    private val binding: DisasterMapFragmentBinding by viewBinding()
    private val viewModel: DisasterMapViewModel by viewModels()
    private lateinit var googleMap: GoogleMap

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpMap()
    }

    private fun setUpMap() {
        val mapFragment =
            childFragmentManager.findFragmentById(binding.mapContainer.id) as SupportMapFragment

        mapFragment.getMapAsync {
            googleMap = it

            // Add a marker in Sydney and move the camera
            val sydney = LatLng(-34.0, 151.0)
            googleMap.addMarker(
                MarkerOptions()
                    .position(sydney)
                    .title("Marker in Sydney")
            )
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
        }
    }
}
