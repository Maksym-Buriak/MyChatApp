package com.maks_buriak.mychat.domain.repository

import com.maks_buriak.mychat.domain.models.AuthUserResult
import com.maks_buriak.mychat.domain.models.User

interface FirebaseAuthRepository {
    suspend fun signInWithGoogle(idToken: String): Result<AuthUserResult>

    fun getCurrentUser(): User?
    fun signOut()
}