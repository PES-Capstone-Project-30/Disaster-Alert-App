package com.jacob.disasteralertapp.common.data.repositories

import com.google.firebase.firestore.CollectionReference
import com.jacob.disasteralertapp.UserCollection
import com.jacob.disasteralertapp.common.data.dtos.UserDetailsDTO
import com.jacob.disasteralertapp.common.data.dtos.toUser
import com.jacob.disasteralertapp.common.models.UserDetails
import javax.inject.Inject
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class UsersRepository @Inject constructor(
    @UserCollection private val userCollection: CollectionReference
) {
    fun getAllUsers() = callbackFlow {
        userCollection.get()
            .await().documents.mapNotNull { it.toObject(UserDetailsDTO::class.java) }
            .map(UserDetailsDTO::toUser)
            .let(::trySend)

        awaitClose()
    }

    fun addUser(userDetails: UserDetails) = userCollection.document(userDetails.id).set(userDetails)
}
