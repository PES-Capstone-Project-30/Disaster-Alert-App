package com.jacob.disasteralertapp.login.ui

import android.content.Context
import android.content.Intent
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.GoogleAuthProvider
import com.jacob.disasteralertapp.common.AuthData
import com.jacob.disasteralertapp.common.data.repositories.UsersRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authData: AuthData,
    private val usersRepository: UsersRepository
) : ViewModel() {
    private val _loginState = MutableSharedFlow<LoginState>()
    val loginState: SharedFlow<LoginState> = _loginState

    fun getGoogleSignInIntent(requestIdToken: String, activity: FragmentActivity): Intent {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(requestIdToken)
            .requestEmail()
            .build()

        return GoogleSignIn.getClient(activity, gso).signInIntent
    }

    fun handleGoogleSignInResult(data: Intent?) {
        updateUiState(LoginState.Loading)
        GoogleSignIn.getSignedInAccountFromIntent(data)
            .addOnSuccessListener { account ->
                val credential = GoogleAuthProvider.getCredential(account.idToken, null)
                authData.firebaseAuth.signInWithCredential(credential)
                    .addOnSuccessListener {
                        val isNewUser = it.additionalUserInfo?.isNewUser ?: true
                        updateUiState(LoginState.UserLoggedIn(isNewUser))
                    }
            }
            .addOnFailureListener { e -> updateUiState(LoginState.Error(e)) }
    }

    private fun updateUiState(loginState: LoginState) {
        viewModelScope.launch {
            _loginState.emit(loginState)
        }
    }

    fun isLoggedIn(context: Context) = authData.isUserPreviouslyLoggedIn(context)
}

sealed class LoginState {
    object Loading : LoginState()
    data class Error(val exception: Exception) : LoginState()
    data class UserLoggedIn(val isNewUser: Boolean) : LoginState()
}
