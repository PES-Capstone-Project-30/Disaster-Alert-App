package com.jacob.disasteralertapp.map.location

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import androidx.hilt.work.HiltWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.google.android.gms.location.LocationServices
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import timber.log.Timber

@HiltWorker
class UpdateLocationWorker @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted private val workerParams: WorkerParameters
) : Worker(context, workerParams) {

    @SuppressLint("MissingPermission")
    override fun doWork(): Result {
        return try {
            if (!isPermissionGranted()) return Result.failure()

            LocationServices.getFusedLocationProviderClient(context)
                .lastLocation
                .addOnSuccessListener { location ->
                }

            Result.success()
        } catch (e: Exception) {
            Timber.e(e, "Exception getting location -->  " + e.message)
            Result.failure()
        }
    }

    private fun isPermissionGranted(): Boolean = ContextCompat.checkSelfPermission(
        context,
        Manifest.permission.ACCESS_FINE_LOCATION
    ) != PackageManager.PERMISSION_GRANTED

    companion object {
        private const val TAG = "UpdateLocationWorker"
        private const val DEFAULT_MIN_INTERVAL = 15L
    }
}
