package com.example.firebasecursogaston.domain.auth

import android.graphics.Bitmap
import com.example.firebasecursogaston.data.remote.auth.AuthDataSource
import com.google.firebase.auth.FirebaseUser

class AuthRepoImpl(private val dataSource: AuthDataSource): AuthRepo {
    override suspend fun signIn(email: String, password: String): FirebaseUser? {
        return dataSource.signIn(email, password)
    }

    override suspend fun signUp(email: String, password: String, username: String): FirebaseUser? = dataSource.signUp(email, password, username)

    override suspend fun updateProfile(imageBitmap: Bitmap, username: String) = dataSource.updateUserProfile(imageBitmap,username)
}