package com.maks_buriak.mychat.domain.usecase

import com.maks_buriak.mychat.domain.repository.PhoneAuthRepository

class VerifyCodeUseCase(private val repository: PhoneAuthRepository) {
    suspend operator fun invoke(verificationId: String, code: String): Result<Unit> =
        repository.verifyCode(verificationId, code)
}