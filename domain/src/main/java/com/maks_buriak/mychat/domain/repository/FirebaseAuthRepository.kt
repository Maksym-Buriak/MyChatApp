package com.maks_buriak.mychat.domain.repository

import com.maks_buriak.mychat.domain.models.User

interface FirebaseAuthRepository {
    suspend fun signInWithGoogle(idToken: String): Result<User>

    fun getCurrentUser(): User?
    fun signOut()
}