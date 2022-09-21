package com.jacob.disasteralertapp

import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.jacob.disasteralertapp.common.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideFirebaseAuth() = Firebase.auth

    @Provides
    @Singleton
    fun provideFirestore() = Firebase.firestore

    @Provides
    @Singleton
    fun provideFirebaseDatabase() = FirebaseDatabase.getInstance()

    @Provides
    @Singleton
    @UserCollection
    fun provideUserCollection(firestore: FirebaseFirestore) =
        firestore.collection(Constants.USER_COLLECTION)

    @Provides
    @Singleton
    @NgoCollection
    fun provideNgoCollection(firestore: FirebaseFirestore) =
        firestore.collection(Constants.NGO_COLLECTION)

    @Provides
    @Singleton
    @NgoWorkersCollection
    fun provideNgoWorkersCollection(firestore: FirebaseFirestore) =
        firestore.collection(Constants.NGO_WORKERS_COLLECTION)
}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class UserCollection

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class NgoCollection

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class NgoWorkersCollection
