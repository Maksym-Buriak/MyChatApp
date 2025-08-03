package com.maks_buriak.mychat.domain.repository

import com.maks_buriak.mychat.domain.models.Message

interface MessageRepository {
    suspend fun sendMessage (message: Message)
}