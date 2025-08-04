package com.maks_buriak.mychat.data.storage.firebase

import com.google.firebase.Firebase
import com.google.firebase.database.database
import com.maks_buriak.mychat.data.storage.models.FirebaseMessageDto
import kotlinx.coroutines.tasks.await

class FirebaseMessageStorage {

    suspend fun writeMessage(dto: FirebaseMessageDto) {
        //підключення до бази даних
        val database = Firebase.database
        val reference = database.getReference("messages").child(dto.id)
        reference.setValue(dto).await()
    }
}