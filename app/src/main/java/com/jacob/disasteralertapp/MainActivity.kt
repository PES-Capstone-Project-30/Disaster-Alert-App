package com.jacob.disasteralertapp

import android.os.Bundle
import android.viewbinding.library.activity.viewBinding
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.jacob.disasteralertapp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
	private val binding: ActivityMainBinding by viewBinding()

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(binding.root)

		val navHostFragment =
			supportFragmentManager.findFragmentById(R.id.main_nav_host_fragment) as NavHostFragment
		val navController = navHostFragment.findNavController()

		binding.bottomNavBar.setupWithNavController(navController)

		navController.addOnDestinationChangedListener { _, destination, _ ->
			val isLoginPage = destination.id == R.id.loginFragment
			val isUserRegistrationPage = destination.id == R.id.userRegistrationDetailsFragment
			val isNgoRegistrationPage = destination.id == R.id.ngoRegistrationDetailsFragment

			if (isLoginPage or isUserRegistrationPage or isNgoRegistrationPage) {
				binding.bottomNavBar.visibility = android.view.View.GONE
			} else {
				binding.bottomNavBar.visibility = android.view.View.VISIBLE
			}
		}
	}
}