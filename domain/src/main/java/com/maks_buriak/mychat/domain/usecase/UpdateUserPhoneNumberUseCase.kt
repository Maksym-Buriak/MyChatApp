package com.maks_buriak.mychat.domain.usecase

import com.maks_buriak.mychat.domain.repository.UserRepository

class UpdateUserPhoneNumberUseCase(private val repository: UserRepository) {
    suspend operator fun invoke(uid: String, phoneNumber: String) = repository.updateUserPhoneNumber(uid, phoneNumber)
}