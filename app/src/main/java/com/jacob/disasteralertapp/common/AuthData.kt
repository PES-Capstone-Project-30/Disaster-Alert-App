package com.jacob.disasteralertapp.common

import android.content.Context
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject

class AuthData @Inject constructor(
    val firebaseAuth: FirebaseAuth
) {
    fun getLoggedInUser() = firebaseAuth.currentUser!!

    fun isUserPreviouslyLoggedIn(context: Context): Boolean {
        GoogleSignIn.getLastSignedInAccount(context) ?: return false
        return true
    }
}
