plugins {
	id("com.android.application") version "7.2.0" apply false
	id("com.android.library") version "7.2.0" apply false
	id("org.jetbrains.kotlin.android") version "1.7.10" apply false
}

buildscript {
	dependencies {
		classpath("com.google.dagger:hilt-android-gradle-plugin:2.42")
        classpath("com.google.gms:google-services:4.3.13")
		classpath("androidx.navigation:navigation-safe-args-gradle-plugin:2.5.1")
	}
}

tasks.register("clean", Delete::class) {
	delete(rootProject.buildDir)
}
