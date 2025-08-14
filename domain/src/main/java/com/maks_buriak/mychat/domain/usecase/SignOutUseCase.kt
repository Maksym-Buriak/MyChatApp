package com.maks_buriak.mychat.domain.usecase

import com.maks_buriak.mychat.domain.repository.FirebaseAuthRepository

class SignOutUseCase(private val repository: FirebaseAuthRepository) {
    operator fun invoke() {
        repository.signOut()
    }
}