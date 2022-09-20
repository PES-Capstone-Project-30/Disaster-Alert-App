package com.jacob.disasteralertapp.common.data.repositories

import com.google.firebase.firestore.CollectionReference
import com.jacob.disasteralertapp.NgoCollection
import com.jacob.disasteralertapp.NgoWorkersCollection
import com.jacob.disasteralertapp.common.data.dtos.NgoOrganizationDetailsDTO
import com.jacob.disasteralertapp.common.data.dtos.NgoWorkerDetailsDTO
import com.jacob.disasteralertapp.common.data.dtos.toNgoOrganizationDetails
import com.jacob.disasteralertapp.common.data.dtos.toNgoUser
import com.jacob.disasteralertapp.common.models.NgoOrganizationDetails
import com.jacob.disasteralertapp.common.models.NgoWorkerDetails
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class NgoRepository @Inject constructor(
    @NgoCollection private val ngoCollection: CollectionReference,
    @NgoWorkersCollection private val ngoWorkersCollection: CollectionReference
) {
    fun getAllNgoWorkers() = callbackFlow {
        ngoWorkersCollection.get()
            .await()
            .documents
            .mapNotNull { it.toObject(NgoWorkerDetailsDTO::class.java) }
            .map(NgoWorkerDetailsDTO::toNgoUser)
            .let(::trySend)

        awaitClose()
    }

    fun getAllNgoOrganizations() = callbackFlow {
        ngoCollection.get()
            .await()
            .documents
            .mapNotNull { it.toObject(NgoOrganizationDetailsDTO::class.java) }
            .map(NgoOrganizationDetailsDTO::toNgoOrganizationDetails)
            .let(::trySend)

        awaitClose()
    }

    fun addNgoWorker(ngoWorkerDetails: NgoWorkerDetails) =
        ngoWorkersCollection.document(ngoWorkerDetails.id).set(ngoWorkerDetails)

    fun addNgoOrganization(ngoOrganizationDetails: NgoOrganizationDetails) =
        ngoWorkersCollection.document(ngoOrganizationDetails.id).set(ngoOrganizationDetails)
}
