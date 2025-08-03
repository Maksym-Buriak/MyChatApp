package com.maks_buriak.mychat.di

import com.maks_buriak.mychat.domain.usecase.SendMessageUseCase
import org.koin.dsl.module

val domainModule = module {

    factory<SendMessageUseCase> {
        SendMessageUseCase(repository = get())
    }
}