package com.jacob.disasteralertapp.map

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.viewbinding.library.fragment.viewBinding
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.jacob.disasteralertapp.R
import com.jacob.disasteralertapp.databinding.DisasterMapFragmentBinding
import dagger.hilt.android.AndroidEntryPoint
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay

@AndroidEntryPoint
class DisasterMapFragment : Fragment(R.layout.disaster_map_fragment) {
    private val binding: DisasterMapFragmentBinding by viewBinding()
    private val viewModel: DisasterMapViewModel by viewModels()

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

    private fun showAlert() = AlertDialog.Builder(requireContext()).let {
        it.setTitle("Need permission(s)")
        it.setMessage(
            "Some permissions are required to open the map. Enable them in application settings."
        )
        it.setNeutralButton("OK", null)
        it.create()
    }.show()

    private fun setUpMap() {
        Toast.makeText(activity, "Setting up map", Toast.LENGTH_SHORT).show()

        binding.map.setTileSource(TileSourceFactory.MAPNIK)

        val mLocationOverlay = MyLocationNewOverlay(
            GpsMyLocationProvider(requireContext()),
            binding.map
        )
        mLocationOverlay.enableMyLocation()
        binding.map.overlays.add(mLocationOverlay)
    }

    override fun onResume() {
        super.onResume()
        binding.map.onResume()
    }

    override fun onPause() {
        super.onPause()
        binding.map.onPause()
    }
}
