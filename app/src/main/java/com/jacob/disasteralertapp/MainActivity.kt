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
class MainActivity : AppCompatActivity(R.layout.activity_main) {
	private val binding: ActivityMainBinding by viewBinding()

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		val navHostFragment =
			supportFragmentManager.findFragmentById(R.id.main_nav_host_fragment) as NavHostFragment

		binding.bottomNavBar.setupWithNavController(navHostFragment.findNavController())
	}
}