package com.jacob.disasteralertapp.common.data.repositories

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.jacob.disasteralertapp.common.data.dtos.LocationDataDTO
import com.jacob.disasteralertapp.common.data.dtos.toLocationData
import com.jacob.disasteralertapp.common.data.dtos.toLocationDataDTO
import com.jacob.disasteralertapp.common.models.BaseUser
import com.jacob.disasteralertapp.common.models.LocationData
import javax.inject.Inject
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow

class UserLocationRepository @Inject constructor(
    private val firebaseDatabase: FirebaseDatabase
) {
    fun getLocationForCity(city: String) = callbackFlow {
        val userLocationDataDbRef = firebaseDatabase.getReference(city).child(USER_LOCATION_DATA)
        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.children
                    .mapNotNull { it.getValue(LocationDataDTO::class.java) }
                    .map(LocationDataDTO::toLocationData)
                    .let(::trySend)
            }

            override fun onCancelled(error: DatabaseError) {
                close(error.toException())
            }
        }

        userLocationDataDbRef.addValueEventListener(listener)

        awaitClose { userLocationDataDbRef.removeEventListener(listener) }
    }

    fun updateUserLocationDetails(userDetails: BaseUser, locationData: LocationData) =
        firebaseDatabase.getReference(userDetails.city)
            .child(USER_LOCATION_DATA)
            .child(userDetails.id)
            .setValue(locationData.toLocationDataDTO())

    companion object {
        private const val USER_LOCATION_DATA = "user_location_data"
    }
}
