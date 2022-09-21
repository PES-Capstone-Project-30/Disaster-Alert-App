package com.jacob.disasteralertapp.common

import android.content.Context
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.firebase.auth.FirebaseAuth
import com.jacob.disasteralertapp.common.models.BaseUser
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthData @Inject constructor(
    val firebaseAuth: FirebaseAuth
) {
    var currentUser: BaseUser? = null
        private set

    fun getLoggedInFirebaseUser() = firebaseAuth.currentUser!!

    fun isUserPreviouslyLoggedIn(context: Context): Boolean {
        GoogleSignIn.getLastSignedInAccount(context) ?: return false
        return true
    }

    fun userLoggedIn(userDetails: BaseUser) {
        currentUser = userDetails
    }
}
