package com.maks_buriak.mychat.di

import com.maks_buriak.mychat.data.repository.MessageRepositoryImpl
import com.maks_buriak.mychat.data.storage.firebase.FirebaseMessageStorage
import com.maks_buriak.mychat.domain.repository.MessageRepository
import org.koin.dsl.module

val dataModule = module {

    single { FirebaseMessageStorage() }

    single<MessageRepository> {
        MessageRepositoryImpl(storage = get()) //імплементація інтерфейсу
    }
}