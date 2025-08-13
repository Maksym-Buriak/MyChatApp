package com.maks_buriak.mychat.di

import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.database
import com.maks_buriak.mychat.data.repository.FirebaseAuthRepositoryImpl
import com.maks_buriak.mychat.data.repository.MessageRepositoryImpl
import com.maks_buriak.mychat.data.storage.firebase.FirebaseMessageStorage
import com.maks_buriak.mychat.domain.repository.FirebaseAuthRepository
import com.maks_buriak.mychat.domain.repository.MessageRepository
import org.koin.dsl.module

val dataModule = module {

    single { Firebase.database }

    single { FirebaseMessageStorage(get()) }

    single<MessageRepository> {
        MessageRepositoryImpl(storage = get()) //імплементація інтерфейсу
    }

    single { FirebaseAuth.getInstance() } //реєструється екземпляр FirebaseAuth
    single<FirebaseAuthRepository> { FirebaseAuthRepositoryImpl(get()) }
}