package com.maks_buriak.mychat.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maks_buriak.mychat.domain.models.Message
import com.maks_buriak.mychat.domain.usecase.SendMessageUseCase
import kotlinx.coroutines.launch

class MessageViewModel(private val sendMessageUseCase: SendMessageUseCase) : ViewModel() {

    fun sendMessage(messageText: String){
        Log.d("MessageViewModel", "sendMessage called with message=$messageText")
        val message = Message (
            //id = UUID.randomUUID().toString(),
            id = "1",
            text = messageText
        )

        viewModelScope.launch {
            try {
                sendMessageUseCase(message)
                Log.d("MessageViewModel", "Message sent successfully.")
            } catch (e: Exception) {
                Log.e("MessageViewModel", "Error sending message", e)
            }
        }
    }
}