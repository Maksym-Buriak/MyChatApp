package com.maks_buriak.mychat.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maks_buriak.mychat.domain.usecase.GetCurrentUserUseCase
import com.maks_buriak.mychat.domain.usecase.SaveUserToFirestoreUseCase
import com.maks_buriak.mychat.domain.usecase.SendVerificationCodeUseCase
import com.maks_buriak.mychat.domain.usecase.UpdateUserPhoneNumberUseCase
import com.maks_buriak.mychat.domain.usecase.VerifyCodeUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PhoneAuthViewModel(
    private val sendVerificationCodeUseCase: SendVerificationCodeUseCase,
    private val verifyCodeUseCase: VerifyCodeUseCase,
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val updateUserPhoneNumberUseCase: UpdateUserPhoneNumberUseCase
) : ViewModel() {

    var verificationId: String? = null

    private val _status = MutableStateFlow<String?>(null)
    val status: StateFlow<String?> = _status

    private val _codeSent = MutableStateFlow(false)
    val codeSent: StateFlow<Boolean> = _codeSent

    fun sendCode(phoneNumber: String) = viewModelScope.launch {
        val result = sendVerificationCodeUseCase(phoneNumber)
        result.onSuccess { id ->
            verificationId = id
            _status.value = "Код відправлено на номер $phoneNumber"
            _codeSent.value = true
        }.onFailure {
            _status.value = "Помилка відправки коду: ${it.message}"
            _codeSent.value = false
        }
    }

    fun verifyCode(code: String, phoneNumber: String, onVerified: () -> Unit) = viewModelScope.launch {
        verificationId?.let { id ->
            val result = verifyCodeUseCase(id, code)
            result.onSuccess {
                // Updating the user phone number
                val currentUser = getCurrentUserUseCase()
                currentUser?.let { user ->

                    updateUserPhoneNumberUseCase(user.uid, phoneNumber)
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