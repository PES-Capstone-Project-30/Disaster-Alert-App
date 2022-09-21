package com.jacob.disasteralertapp.common.data.repositories

import com.google.firebase.database.FirebaseDatabase
import javax.inject.Inject
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class DisasterDataRepository @Inject constructor(
    private val firebaseDatabase: FirebaseDatabase
) {
    fun isDisasterActive(city: String) = callbackFlow {
        firebaseDatabase.getReference(city)
            .child(DISASTER_DATA)
            .child(IS_ACTIVE)
            .get()
            .await()
            .getValue(Boolean::class.java)
            .let { trySend(it ?: false) }
    }

    companion object {
        private const val DISASTER_DATA = "disaster_data"
        private const val IS_ACTIVE = "is_active"
    }
}
