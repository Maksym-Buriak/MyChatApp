package com.maks_buriak.mychat.domain.usecase

import com.maks_buriak.mychat.domain.repository.UserRepository

class IsNickNameTakenUseCase(private val repository: UserRepository) {
    suspend operator fun invoke(nickName: String): Boolean =
        repository.isNickNameTaken(nickName)
}