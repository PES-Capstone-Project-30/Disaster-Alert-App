package com.jacob.disasteralertapp.common.workers

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import androidx.core.content.ContextCompat
import androidx.hilt.work.HiltWorker
import androidx.work.*
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
import java.time.Duration
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
            val baseUser = authData.currentUser ?: firebaseUser.getLoggedInUserDetails()

            if (baseUser == null) {
                Timber.e("baseUser is null")
                return Result.failure()
            }

            if (!isDisasterActive(baseUser)) {
                WorkManager.getInstance(context).cancelAllWorkByTag(TAG)
                Timber.w("Disaster is not active, cancelling work")
                return Result.success()
            }

            if (!isPermissionGranted()) {
                Timber.e("Location permission not granted")
                return Result.failure()
            }

            LocationServices.getFusedLocationProviderClient(context)
                .lastLocation
                .addOnCompleteListener { locationTask ->
                    if (!locationTask.isSuccessful) {
                        Timber.e("Failed to get location")
                        return@addOnCompleteListener
                    }

                    val location = locationTask.result
                    if (location == null) {
                        Timber.e("Location is null")
                        return@addOnCompleteListener
                    }

                    val locationData = location.getLocationData(baseUser)
                    userLocationRepository.updateUserLocationDetails(baseUser, locationData)
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
    ) == PackageManager.PERMISSION_GRANTED

    companion object {
        private const val TAG = "UpdateLocationWorker"
        private const val DEFAULT_MIN_INTERVAL = 15L

        fun createWorkerRequest(context: Context) {
            val workRequest =
                PeriodicWorkRequestBuilder<UpdateLocationWorker>(
                    Duration.ofMinutes(DEFAULT_MIN_INTERVAL)
                )
                    .addTag(TAG)
                    .build()

            WorkManager.getInstance(context)
                .enqueueUniquePeriodicWork(
                    TAG,
                    ExistingPeriodicWorkPolicy.KEEP,
                    workRequest
                )
        }
    }
}
