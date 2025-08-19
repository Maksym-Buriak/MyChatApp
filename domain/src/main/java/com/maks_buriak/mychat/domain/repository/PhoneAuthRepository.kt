package com.maks_buriak.mychat.domain.repository

interface PhoneAuthRepository {
    suspend fun sendVerificationCode(phoneNumber: String): Result<String> // return verificationId
    suspend fun verifyCode(verificationId: String, code: String): Result<Unit>
}