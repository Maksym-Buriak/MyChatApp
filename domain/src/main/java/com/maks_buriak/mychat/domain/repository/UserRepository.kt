package com.maks_buriak.mychat.domain.repository

import com.maks_buriak.mychat.domain.models.User

//interface for Firebase Firestore DB
interface UserRepository {
    suspend fun saveUser(user: User)
    suspend fun getUserByUid(uid: String): User?
    suspend fun updateUserPhoneNumber(uid: String, phoneNumber: String)
}