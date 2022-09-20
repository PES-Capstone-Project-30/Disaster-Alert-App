package com.jacob.disasteralertapp.common.data.repositories

import com.google.firebase.firestore.CollectionReference
import com.jacob.disasteralertapp.UserCollection
import com.jacob.disasteralertapp.common.data.dtos.UserDTO
import com.jacob.disasteralertapp.common.data.dtos.toUser
import com.jacob.disasteralertapp.common.models.User
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class UsersRepository @Inject constructor(
	@UserCollection private val userCollection: CollectionReference,
) {
	fun getAllUsers() = callbackFlow {
		userCollection.get()
			.await().documents.mapNotNull { it.toObject(UserDTO::class.java) }
			.map(UserDTO::toUser)
			.let(::trySend)

		awaitClose()
	}

	fun addUser(user: User) = userCollection.document(user.id).set(user)
}