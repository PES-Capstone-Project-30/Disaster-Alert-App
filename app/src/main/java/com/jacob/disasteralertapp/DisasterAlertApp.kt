package com.jacob.disasteralertapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import timber.log.Timber.DebugTree

@HiltAndroidApp
class DisasterAlertApp : Application() {
	override fun onCreate() {
		super.onCreate()

		Timber.plant(DebugTree())
	}
}
