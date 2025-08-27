package com.maks_buriak.mychat.domain.usecase

import com.maks_buriak.mychat.domain.models.User
import com.maks_buriak.mychat.domain.repository.UserRepository

class GetUserByUidUseCase(private val repository: UserRepository) {
    suspend operator fun invoke(uid: String): User? {
        return repository.getUserByUid(uid)
    }
}