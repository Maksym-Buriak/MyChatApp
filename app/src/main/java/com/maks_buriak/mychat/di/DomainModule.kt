package com.maks_buriak.mychat.di

import com.maks_buriak.mychat.domain.usecase.SendMessageUseCase
import com.maks_buriak.mychat.domain.usecase.SignInWithGoogleUseCase
import org.koin.dsl.module

val domainModule = module {

    factory<SendMessageUseCase> {
        SendMessageUseCase(repository = get())
    }

    factory<SignInWithGoogleUseCase> {
        SignInWithGoogleUseCase(repository = get())
    }
}