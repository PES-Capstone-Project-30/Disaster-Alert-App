package com.jacob.disasteralertapp.map

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.viewbinding.library.fragment.viewBinding
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.jacob.disasteralertapp.R
import com.jacob.disasteralertapp.databinding.DisasterMapFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DisasterMapFragment : Fragment(R.layout.disaster_map_fragment) {
    private val binding: DisasterMapFragmentBinding by viewBinding()
    private val viewModel: DisasterMapViewModel by viewModels()
    private lateinit var googleMap: GoogleMap

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            when (isGranted) {
                true -> setUpMap()
                false -> showAlert()
            }
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        when {
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED -> setUpMap()
            shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION) -> showAlert()
            else -> requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    @Suppress("MissingPermission")
    private fun setUpMap() {
        val mapFragment =
            childFragmentManager.findFragmentById(binding.mapContainer.id) as SupportMapFragment

        mapFragment.getMapAsync {
            googleMap = it

            googleMap.isMyLocationEnabled = true
        }
    }

    private fun showAlert() = AlertDialog.Builder(requireContext()).let {
        it.setTitle("Need permission(s)")
        it.setMessage(
            "Some permissions are required to open the map. Enable them in application settings."
        )
        it.setNeutralButton("OK", null)
        it.create()
    }.show()
}
