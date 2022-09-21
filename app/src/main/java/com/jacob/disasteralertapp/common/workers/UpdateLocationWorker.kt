package com.jacob.disasteralertapp.common.workers

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import androidx.core.content.ContextCompat
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import com.google.android.gms.location.LocationServices
import com.google.firebase.auth.FirebaseUser
import com.jacob.disasteralertapp.common.AuthData
import com.jacob.disasteralertapp.common.data.repositories.DisasterDataRepository
import com.jacob.disasteralertapp.common.data.repositories.NgoRepository
import com.jacob.disasteralertapp.common.data.repositories.UserLocationRepository
import com.jacob.disasteralertapp.common.data.repositories.UsersRepository
import com.jacob.disasteralertapp.common.models.*
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.firstOrNull
import timber.log.Timber

@HiltWorker
class UpdateLocationWorker @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted private val workerParams: WorkerParameters,
    private val authData: AuthData,
    private val userLocationRepository: UserLocationRepository,
    private val disasterDataRepository: DisasterDataRepository,
    private val usersRepository: UsersRepository,
    private val ngoRepository: NgoRepository
) : CoroutineWorker(context, workerParams) {

    @SuppressLint("MissingPermission")
    override suspend fun doWork(): Result {
        return try {
            val firebaseUser = authData.getLoggedInFirebaseUser()
            val currentUser = firebaseUser.getLoggedInUserDetails() ?: return Result.failure()

            if (!isDisasterActive(currentUser)) {
                WorkManager.getInstance(context).cancelAllWorkByTag(TAG)
                return Result.success()
            }

            if (!isPermissionGranted()) return Result.failure()

            LocationServices.getFusedLocationProviderClient(context)
                .lastLocation
                .addOnCompleteListener { locationTask ->
                    if (!locationTask.isSuccessful) return@addOnCompleteListener
                    val location = locationTask.result ?: return@addOnCompleteListener

                    val locationData = location.getLocationData(currentUser)
                    userLocationRepository.updateUserLocationDetails(currentUser, locationData)
                }

            Result.success()
        } catch (e: Exception) {
            Timber.e(e, "Exception getting location --> ${e.message}")
            Result.failure()
        }
    }

    private fun Location.getLocationData(currentUser: BaseUser) = LocationData(
        latitude = latitude,
        longitude = longitude,
        name = currentUser.displayName,
        type = when (currentUser) {
            is UserDetails -> UserType.USER
            is NgoWorkerDetails -> UserType.NGO_WORKER
        }
    )

    private suspend fun isDisasterActive(currentUser: BaseUser) =
        disasterDataRepository.isDisasterActive(currentUser.city).firstOrNull()
            ?: false

    private suspend fun FirebaseUser.getLoggedInUserDetails() =
        usersRepository.getUserById(uid).firstOrNull()
            ?: ngoRepository.findNgoWorkerById(uid).firstOrNull()

    private fun isPermissionGranted() = ContextCompat.checkSelfPermission(
        context,
        Manifest.permission.ACCESS_FINE_LOCATION
    ) != PackageManager.PERMISSION_GRANTED

    companion object {
        private const val TAG = "UpdateLocationWorker"
        private const val DEFAULT_MIN_INTERVAL = 15L
    }
}
