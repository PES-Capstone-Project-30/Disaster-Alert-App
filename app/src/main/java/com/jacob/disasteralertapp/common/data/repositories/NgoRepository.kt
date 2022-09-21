package com.jacob.disasteralertapp.common.data.repositories

import com.google.firebase.firestore.CollectionReference
import com.jacob.disasteralertapp.NgoCollection
import com.jacob.disasteralertapp.NgoWorkersCollection
import com.jacob.disasteralertapp.common.data.dtos.*
import com.jacob.disasteralertapp.common.models.NgoOrganizationDetails
import com.jacob.disasteralertapp.common.models.NgoWorkerDetails
import javax.inject.Inject
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class NgoRepository @Inject constructor(
    @NgoCollection private val ngoCollection: CollectionReference,
    @NgoWorkersCollection private val ngoWorkersCollection: CollectionReference
) {
    fun getAllNgoWorkers() = callbackFlow {
        ngoWorkersCollection.get()
            .await()
            .documents
            .mapNotNull { it.toObject(NgoWorkerDetailsDTO::class.java) }
            .map(NgoWorkerDetailsDTO::toNgoWorker)
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

    fun findNgoWorkerById(id: String) = callbackFlow {
        ngoWorkersCollection.document(id)
            .get()
            .await()
            .toObject(NgoWorkerDetailsDTO::class.java)
            ?.toNgoWorker()
            ?.let(::trySend)

        awaitClose()
    }

    fun addNgoWorker(ngoWorkerDetails: NgoWorkerDetails) =
        ngoWorkersCollection.document(ngoWorkerDetails.id).set(ngoWorkerDetails.toNgoWorkerDTO())

    fun addNgoOrganization(ngoOrganizationDetails: NgoOrganizationDetails) =
        ngoWorkersCollection.document(ngoOrganizationDetails.id)
            .set(ngoOrganizationDetails.toNgoOrganizationDetailsDTO())
}
