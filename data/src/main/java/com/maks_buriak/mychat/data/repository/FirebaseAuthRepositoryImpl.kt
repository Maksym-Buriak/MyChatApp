package com.maks_buriak.mychat.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.maks_buriak.mychat.data.storage.models.FirebaseUserDto
import com.maks_buriak.mychat.domain.models.User
import com.maks_buriak.mychat.domain.repository.FirebaseAuthRepository
import kotlinx.coroutines.tasks.await

class FirebaseAuthRepositoryImpl(private val firebaseAuth: FirebaseAuth) : FirebaseAuthRepository {
    override suspend fun signInWithGoogle(idToken: String): Result<User> {
        return try {
            val credential = GoogleAuthProvider.getCredential(idToken, null)
            val authResult = firebaseAuth.signInWithCredential(credential).await()
            val firebaseUser = authResult.user
                ?: return Result.failure(Exception("User is null"))

            val dto = FirebaseUserDto.fromFirebaseUser(firebaseUser)
            Result.success(dto.toDomain())

        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override fun getCurrentUser(): User? {
        val user = firebaseAuth.currentUser ?: return null
        return FirebaseUserDto.fromFirebaseUser(user).toDomain()
    }

    override fun signOut() {
        firebaseAuth.signOut()
    }

    fun FirebaseUserDto.toDomain(): User {
        return User(
            uid = uid,
            displayName = displayName,
            email = email,
            photoUrl = photoUrl
        )
    }
}