package com.maks_buriak.mychat.domain.usecase

import com.maks_buriak.mychat.domain.models.User
import com.maks_buriak.mychat.domain.repository.UserRepository

class SaveUserToFirestoreUseCase(private val repository: UserRepository) {
    suspend operator fun invoke(user: User) {
        repository.saveUser(user)
    }
}