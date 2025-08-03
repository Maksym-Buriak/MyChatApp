package com.maks_buriak.mychat.domain.usecase

import com.maks_buriak.mychat.domain.models.Message
import com.maks_buriak.mychat.domain.repository.MessageRepository

class SendMessageUseCase(private val repository: MessageRepository) {

    suspend operator fun invoke(message: Message) {
        repository.sendMessage(message)
    }
}