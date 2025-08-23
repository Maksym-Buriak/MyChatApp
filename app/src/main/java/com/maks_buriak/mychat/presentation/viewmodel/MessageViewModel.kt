package com.maks_buriak.mychat.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.maks_buriak.mychat.domain.models.Message
import com.maks_buriak.mychat.domain.models.User
import com.maks_buriak.mychat.domain.usecase.SendMessageUseCase
import com.maks_buriak.mychat.presentation.UserManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.UUID

class MessageViewModel(
    private val sendMessageUseCase: SendMessageUseCase,
    private val userManager: UserManager
) : ViewModel() {

    val currentUser: StateFlow<User?> = userManager.currentUser

    private val _uiMessage = MutableStateFlow<String?>(null)
    val uiMessage: StateFlow<String?> get() = _uiMessage


    fun sendMessage(messageText: String){
        Log.d("MessageViewModel", "sendMessage called with message=$messageText")

        //val user = FirebaseAuth.getInstance().currentUser
        val firebaseUser = FirebaseAuth.getInstance().currentUser
        if (firebaseUser == null) {
            Log.e("MessageViewModel", "No FirebaseUser available")
            return
        }

        val message = Message (
            id = UUID.randomUUID().toString(),
            //id = "1",
            text = messageText
        )
        firebaseUser.getIdToken(true).addOnCompleteListener { task ->

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
                viewModelScope.launch {
                    userManager.refreshUser()
                }
            }

        }
    }

    fun signOut() {
        userManager.logout()
    }

    fun checkPhoneVerification(onNeedVerification: () -> Unit) {
        
        val action = getPhoneAction()

        when (action) {
            PhoneAction.ADD -> {
                onNeedVerification()
                _uiMessage.value = "Додайте свій номер телефону"
            }
            PhoneAction.CHANGE -> {
                onNeedVerification()
                _uiMessage.value = "Для зміни номера телефону введіть його у відповідне поле та підтвердіть"
            }
        }
    }

    fun clearUiMessage() {
        _uiMessage.value = null
    }

    enum class PhoneAction {
        ADD, CHANGE
    }

    fun getPhoneAction(): PhoneAction {
        return if (currentUser.value?.phoneNumber.isNullOrEmpty()) PhoneAction.ADD
        else PhoneAction.CHANGE
    }
}