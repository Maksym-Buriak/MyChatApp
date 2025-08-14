package com.maks_buriak.mychat.domain.usecase

import com.maks_buriak.mychat.domain.models.User
import com.maks_buriak.mychat.domain.repository.FirebaseAuthRepository

class GetCurrentUserUseCase(private val repository: FirebaseAuthRepository) {
    operator fun invoke(): User? {
        return repository.getCurrentUser()
    }
}