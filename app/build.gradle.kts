@file:Suppress("UnstableApiUsage", "SpellCheckingInspection")

import android.annotation.SuppressLint

plugins {
	id("com.android.application")
	id("org.jetbrains.kotlin.android")
	id("kotlin-android")

	id("androidx.navigation.safeargs.kotlin")
	kotlin("kapt")
	id("dagger.hilt.android.plugin")
	id("com.google.gms.google-services")
}
android {
	compileSdk = 32

	defaultConfig {
		applicationId = "com.jacob.disasteralertapp"
		minSdk = 29
		@SuppressLint("OldTargetApi")
		targetSdk = 32
		versionCode = 1
		versionName = "1.0"

		testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
	}

	buildTypes {
		getByName("release") {
			isMinifyEnabled = true
			proguardFiles(
				getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro"
			)
		}
	}

	compileOptions {
		sourceCompatibility = JavaVersion.VERSION_11
		targetCompatibility = JavaVersion.VERSION_11
	}

	kotlinOptions {
		jvmTarget = "11"
	}

	buildFeatures {
		viewBinding = true
	}
}

dependencies {
	implementation("androidx.legacy:legacy-support-v4:1.0.0")
	implementation("androidx.core:core-ktx:1.8.0")
	implementation("androidx.appcompat:appcompat:1.5.0")
	implementation("com.google.android.material:material:1.6.1")
	implementation("androidx.constraintlayout:constraintlayout:2.1.4")

	implementation("androidx.navigation:navigation-fragment-ktx:2.5.1")
	implementation("androidx.navigation:navigation-ui-ktx:2.5.1")

	implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.5.1")
	implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.5.1")

	implementation("com.google.dagger:hilt-android:2.43.1")
	kapt("com.google.dagger:hilt-android-compiler:2.43.1")

	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-play-services:1.6.4")

	implementation("com.google.android.gms:play-services-auth:20.3.0")
	implementation(platform("com.google.firebase:firebase-bom:30.3.2"))
	implementation("com.google.firebase:firebase-auth-ktx")
	implementation("com.google.firebase:firebase-firestore-ktx")
	implementation("com.google.firebase:firebase-analytics-ktx")
	implementation("com.google.firebase:firebase-messaging-ktx")

	implementation("com.github.yogacp:android-viewbinding:1.0.4")
	implementation("com.jakewharton.timber:timber:5.0.1")

	testImplementation("junit:junit:4.13.2")
	androidTestImplementation("androidx.test.ext:junit:1.1.3")
	androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
}

kapt {
	correctErrorTypes = true
}
