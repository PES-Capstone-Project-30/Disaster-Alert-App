@file:Suppress("UnstableApiUsage")

plugins {
	id("com.android.application")
	id("org.jetbrains.kotlin.android")
	id("kotlin-android")
}

android {
	compileSdk = 32

	defaultConfig {
		applicationId = "com.jacob.disasteralertapp"
		minSdk = 29
		targetSdk = 32
		versionCode = 1
		versionName = "1.0"

		testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
	}

	buildTypes {
		getByName("release") {
			isMinifyEnabled = true
			proguardFiles(
				getDefaultProguardFile("proguard-android.txt"),
				"proguard-rules.pro"
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

	sourceSets {
		getByName("main") {
			res {
				srcDirs("src/main/res", "src/main/res/layout/login")
			}
		}
	}
}

dependencies {
	val navigationVersion = "2.5.1"

	implementation("androidx.legacy:legacy-support-v4:1.0.0")
	implementation("androidx.core:core-ktx:1.8.0")
	implementation("androidx.appcompat:appcompat:1.4.2")
	implementation("com.google.android.material:material:1.6.1")
	implementation("androidx.constraintlayout:constraintlayout:2.1.4")

	implementation("androidx.navigation:navigation-fragment-ktx:$navigationVersion")
	implementation("androidx.navigation:navigation-ui-ktx:$navigationVersion")

	implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.5.1")
	implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.5.1")

	implementation("com.github.yogacp:android-viewbinding:1.0.4")

	testImplementation("junit:junit:4.13.2")
	androidTestImplementation("androidx.test.ext:junit:1.1.3")
	androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
}
