package com.maks_buriak.mychat.data.repository

import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.maks_buriak.mychat.domain.models.User
import com.maks_buriak.mychat.domain.repository.UserRepository
import kotlinx.coroutines.tasks.await

class UserRepositoryImpl(private val firestore: FirebaseFirestore) : UserRepository {

    override suspend fun saveUser(user: User) {
        val userMap = hashMapOf(
            "uid" to user.uid,
            "displayName" to user.displayName,
            "email" to user.email,
            "photoUrl" to user.photoUrl,
            "phoneNumber" to user.phoneNumber,
            "createdAt" to FieldValue.serverTimestamp() // серверний час
        )

        firestore.collection("users")
            .document(user.uid)
            .set(userMap, SetOptions.merge())
            .await()
    }

    override suspend fun getUserByUid(uid: String): User? {
        val snapshot = firestore.collection("users")
            .document(uid)
            .get()
            .await()
        return snapshot.toObject(User::class.java)
    }
}