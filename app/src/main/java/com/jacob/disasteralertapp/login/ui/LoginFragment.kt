package com.jacob.disasteralertapp.login.ui

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.viewbinding.library.fragment.viewBinding
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.jacob.disasteralertapp.R
import com.jacob.disasteralertapp.databinding.LoginFragmentBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class LoginFragment : Fragment(R.layout.login_fragment) {
    private val binding: LoginFragmentBinding by viewBinding()
    private val viewModel: LoginViewModel by viewModels()
    private lateinit var directions: NavDirections

    private val resultLauncher = registerForActivityResult(StartActivityForResult()) { result ->
        when (result?.resultCode) {
            Activity.RESULT_OK -> viewModel.handleGoogleSignInResult(result.data)
            null -> Unit
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            viewModel.loginState
                .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .collect {
                    when (it) {
                        LoginState.Loading -> Unit
                        is LoginState.Error -> Timber.e(it.exception)
                        is LoginState.UserLoggedIn -> {
                            when (it.isNewUser) {
                                false -> findNavController().navigate(
                                    LoginFragmentDirections.toHomeFragment()
                                )
                                true -> findNavController().navigate(directions)
                            }
                        }
                    }
                }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.userSignInButton.setOnClickListener {
            directions = LoginFragmentDirections.toUserRegistrationDetailsFragment()
            googleSignIn()
        }
        binding.ngoSignInButton.setOnClickListener {
            directions = LoginFragmentDirections.toNgoRegistrationDetailsFragment()
            googleSignIn()
        }
    }

    private fun googleSignIn() = resultLauncher.launch(
        viewModel.getGoogleSignInIntent(
            getString(R.string.default_web_client_id),
            requireActivity()
        )
    )
}
