package com.maks_buriak.mychat.di

import com.maks_buriak.mychat.domain.usecase.GetCurrentUserUseCase
import com.maks_buriak.mychat.domain.usecase.SendMessageUseCase
import com.maks_buriak.mychat.domain.usecase.SignInWithGoogleUseCase
import com.maks_buriak.mychat.domain.usecase.SignOutUseCase
import org.koin.dsl.module

val domainModule = module {

    factory<SendMessageUseCase> {
        SendMessageUseCase(repository = get())
    }

    factory<SignInWithGoogleUseCase> {
        SignInWithGoogleUseCase(repository = get())
    }

    factory<SignOutUseCase> {
        SignOutUseCase(repository = get())
    }

    factory<GetCurrentUserUseCase> {
        GetCurrentUserUseCase(repository = get())
    }
}