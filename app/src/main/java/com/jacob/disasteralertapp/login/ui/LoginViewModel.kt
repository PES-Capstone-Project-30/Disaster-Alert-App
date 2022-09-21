package com.jacob.disasteralertapp.login.ui

import android.app.Application
import android.content.Intent
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.GoogleAuthProvider
import com.jacob.disasteralertapp.common.AuthData
import com.jacob.disasteralertapp.common.data.repositories.UsersRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

@HiltViewModel
class LoginViewModel @Inject constructor(
    application: Application,
    private val authData: AuthData,
    private val usersRepository: UsersRepository
) : AndroidViewModel(application) {
    private val _loginState = MutableSharedFlow<LoginState>()
    val loginState: SharedFlow<LoginState> = _loginState

    private val context
        get() = getApplication<Application>()

    init {
        if (authData.isUserPreviouslyLoggedIn(context)) {
            viewModelScope.launch {
                usersRepository.getUserById(authData.getLoggedInFirebaseUser().uid)
                    .firstOrNull()
                    ?.let(authData::userLoggedIn)
                updateUiState(LoginState.UserLoggedIn(isNewUser = false))
            }
        }
    }

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
}

sealed class LoginState {
    object Loading : LoginState()
    data class Error(val exception: Exception) : LoginState()
    data class UserLoggedIn(val isNewUser: Boolean) : LoginState()
}
