package com.maks_buriak.mychat.di

import com.maks_buriak.mychat.presentation.viewmodel.AuthViewModel
import com.maks_buriak.mychat.presentation.viewmodel.MessageViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    viewModel<MessageViewModel> {
        MessageViewModel(
            sendMessageUseCase = get()
        )
    }

    viewModel<AuthViewModel> {
        AuthViewModel(
            signInWithGoogleUseCase = get(),
            authRepository = get()
        )
    }
}