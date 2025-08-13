package com.maks_buriak.mychat.domain.usecase

import com.maks_buriak.mychat.domain.models.User
import com.maks_buriak.mychat.domain.repository.FirebaseAuthRepository

class SignInWithGoogleUseCase(private val repository: FirebaseAuthRepository) {
    suspend operator fun invoke(idToken: String) = repository.signInWithGoogle(idToken)

}