package com.maks_buriak.mychat.domain.usecase

import com.maks_buriak.mychat.domain.repository.UserRepository

class UpdateUserNickUseCase(private val repository: UserRepository) {
    suspend operator fun invoke(uid: String, nickName: String) =
        repository.updateUserNick(uid, nickName)
}