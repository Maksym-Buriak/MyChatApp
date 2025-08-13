package com.maks_buriak.mychat.data.storage.models

import com.google.firebase.auth.FirebaseUser

data class FirebaseUserDto(
    val uid: String,
    val displayName: String?,
    val email: String?,
    val photoUrl: String?
) {
    companion object {
        fun fromFirebaseUser(user: FirebaseUser) = FirebaseUserDto(
            uid = user.uid,
            displayName = user.displayName,
            email = user.email,
            photoUrl = user.photoUrl?.toString()
        )
    }
}