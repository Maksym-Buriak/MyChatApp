package com.maks_buriak.mychat.domain.usecase

import com.maks_buriak.mychat.domain.repository.PhoneAuthRepository

class SendVerificationCodeUseCase(private val repository: PhoneAuthRepository) {
    suspend operator fun invoke(phoneNumber: String): Result<String> = repository.sendVerificationCode(phoneNumber)
}