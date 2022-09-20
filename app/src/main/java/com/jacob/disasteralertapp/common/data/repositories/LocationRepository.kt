package com.jacob.disasteralertapp.common.data.repositories

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.jacob.disasteralertapp.common.data.dtos.LocationDataDTO
import com.jacob.disasteralertapp.common.data.dtos.toLocationData
import javax.inject.Inject
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.callbackFlow

class LocationRepository @Inject constructor(
    private val firebaseDatabase: FirebaseDatabase
) {
    fun getLocationForCity(city: String) = callbackFlow {
        val reference = firebaseDatabase.getReference(city)
        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.children
                    .mapNotNull { it.getValue(LocationDataDTO::class.java) }
                    .map(LocationDataDTO::toLocationData)
                    .let(::trySendBlocking)
            }

            override fun onCancelled(error: DatabaseError) {
                close(error.toException())
            }
        }

        reference.addValueEventListener(listener)

        awaitClose { reference.removeEventListener(listener) }
    }
}
