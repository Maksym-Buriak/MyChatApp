package com.maks_buriak.mychat.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maks_buriak.mychat.domain.usecase.GetCurrentUserUseCase
import com.maks_buriak.mychat.domain.usecase.SaveUserToFirestoreUseCase
import com.maks_buriak.mychat.domain.usecase.SendVerificationCodeUseCase
import com.maks_buriak.mychat.domain.usecase.VerifyCodeUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PhoneAuthViewModel(
    private val sendVerificationCodeUseCase: SendVerificationCodeUseCase,
    private val verifyCodeUseCase: VerifyCodeUseCase,
    private val saveUserToFirestoreUseCase: SaveUserToFirestoreUseCase,
    private val getCurrentUserUseCase: GetCurrentUserUseCase
) : ViewModel() {

    var verificationId: String? = null

    private val _status = MutableStateFlow<String?>(null)
    val status: StateFlow<String?> = _status

    fun sendCode(phoneNumber: String) = viewModelScope.launch {
        val result = sendVerificationCodeUseCase(phoneNumber)
        result.onSuccess { id ->
            verificationId = id
            _status.value = "Код відправлено на номер $phoneNumber"
        }.onFailure {
            _status.value = "Помилка відправки коду: ${it.message}"
        }
    }

    fun verifyCode(code: String, phoneNumber: String, onVerified: () -> Unit) = viewModelScope.launch {
        verificationId?.let { id ->
            val result = verifyCodeUseCase(id, code)
            result.onSuccess {
                // Updating the user phone number
                val currentUser = getCurrentUserUseCase()
                currentUser?.let { user ->
                    val updatedUser = user.copy(phoneNumber = phoneNumber)
                    saveUserToFirestoreUseCase(updatedUser)
                }
                onVerified()
            }.onFailure {
                _status.value = "Неправильний код: ${it.message}"
            }
        } ?: run {
            _status.value = "Спочатку надішліть код"
        }
    }
}