package com.maks_buriak.mychat.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.maks_buriak.mychat.domain.models.Message
import com.maks_buriak.mychat.domain.models.User
import com.maks_buriak.mychat.domain.usecase.GetCurrentUserUseCase
import com.maks_buriak.mychat.domain.usecase.SendMessageUseCase
import com.maks_buriak.mychat.domain.usecase.SignOutUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.UUID

class MessageViewModel(
    private val sendMessageUseCase: SendMessageUseCase,
    private val signOutUseCase: SignOutUseCase,
    getCurrentUserUseCase: GetCurrentUserUseCase
) : ViewModel() {

    val currentUser: User? = getCurrentUserUseCase()

    private val _uiMessage = MutableStateFlow<String?>(null)
    val uiMessage: StateFlow<String?> get() = _uiMessage

    fun sendMessage(messageText: String){
        Log.d("MessageViewModel", "sendMessage called with message=$messageText")

        val user = FirebaseAuth.getInstance().currentUser
        val message = Message (
            id = UUID.randomUUID().toString(),
            //id = "1",
            text = messageText
        )
        user?.getIdToken(true)?.addOnCompleteListener { task ->

            if (task.isSuccessful) {
                // токен валідний — відправляємо повідомлення
                viewModelScope.launch {
                    try {
                        sendMessageUseCase(message)
                        Log.d("MessageViewModel", "Message sent successfully.")
                    } catch (e: Exception) {
                        Log.e("MessageViewModel", "Error sending message", e)
                    }
                }
            } else {
                // токен відкликано -> вихід
                FirebaseAuth.getInstance().signOut()
            }

        }
    }

    fun signOut() {
        signOutUseCase()
    }

    fun checkPhoneVerification(onNeedVerification: () -> Unit) {
        val user = FirebaseAuth.getInstance().currentUser
        if (user?.phoneNumber.isNullOrEmpty()) {
            onNeedVerification()
        } else {
            _uiMessage.value = "Номер телефону вже підтверджено"
        }
    }

    fun clearUiMessage() {
        _uiMessage.value = null
    }
}