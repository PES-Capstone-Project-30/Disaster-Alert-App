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
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory.HUE_AZURE
import com.google.android.gms.maps.model.BitmapDescriptorFactory.defaultMarker
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.jacob.disasteralertapp.common.models.LocationData
import com.jacob.disasteralertapp.databinding.DisasterMapFragmentBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DisasterMapFragment : Fragment(com.jacob.disasteralertapp.R.layout.disaster_map_fragment) {
    private val binding: DisasterMapFragmentBinding by viewBinding()
    private val viewModel: DisasterMapViewModel by viewModels()
    private val markerList: MutableList<Marker> = mutableListOf()
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
            locationPermissionIsGranted() -> setUpMap()
            locationPermissionIsNotGranted() -> showAlert()
            else -> requestLocationPermission()
        }
    }

    @Suppress("MissingPermission")
    private fun setUpMap() {
        val mapFragment =
            childFragmentManager.findFragmentById(binding.mapContainer.id) as SupportMapFragment

        mapFragment.getMapAsync {
            googleMap = it

            googleMap.isMyLocationEnabled = true

            LocationServices.getFusedLocationProviderClient(requireContext())
                .lastLocation
                .addOnCompleteListener { locationTask ->
                    if (!locationTask.isSuccessful) return@addOnCompleteListener
                    val location = locationTask.result ?: return@addOnCompleteListener

                    val latLng = LatLng(location.latitude, location.longitude)
                    val cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 14f)
                    googleMap.animateCamera(cameraUpdate)
                }

            googleMap.setOnMarkerClickListener { marker ->
                val locationData = marker.tag as LocationData
                AlertDialog.Builder(requireActivity())
                    .setTitle(locationData.name)
                    .setMessage(locationData.toString())
                    .show()

                true
            }
            showMarkers()
        }
    }

    private fun showMarkers() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.locationDataFlow.collect {
                    markerList.forEach(Marker::remove)

                    it.mapNotNull { locationData ->
                        val markerOptions = MarkerOptions()
                            .position(LatLng(locationData.latitude, locationData.longitude))
                            .draggable(false)
                            .icon(defaultMarker(HUE_AZURE))
                            .title(locationData.name)

                        googleMap.addMarker(markerOptions)?.also { marker ->
                            marker.tag = locationData
                        }
                    }
                        .let(markerList::addAll)
                }
            }
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

    private fun locationPermissionIsNotGranted() =
        shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)

    private fun locationPermissionIsGranted() = ContextCompat.checkSelfPermission(
        requireContext(),
        Manifest.permission.ACCESS_FINE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED

    private fun requestLocationPermission() =
        requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
}
