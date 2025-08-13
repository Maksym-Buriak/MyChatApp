package com.maks_buriak.mychat.data.storage.models

//спеціальна модель для роботи з Firebase, не впливає на domain модель.
data class FirebaseMessageDto (
    val id: String = "",
    val text: String = ""
)