package com.maks_buriak.mychat.di

import com.maks_buriak.mychat.R
import com.maks_buriak.mychat.data.authentication.google.GoogleSignInHelper
import com.maks_buriak.mychat.presentation.viewmodel.AuthViewModel
import com.maks_buriak.mychat.presentation.viewmodel.MessageViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    single {
        GoogleSignInHelper(
            context = androidContext(),
            webClientId = androidContext().getString(R.string.default_web_client_id)
        )
    }

    viewModel<MessageViewModel> {
        MessageViewModel(
            sendMessageUseCase = get(),
            signOutUseCase = get(),
            getCurrentUserUseCase = get()
        )
    }

    viewModel<AuthViewModel> {
        AuthViewModel(
            signInWithGoogleUseCase = get(),
            getCurrentUserUseCase = get(),
            googleSignInHelper = get(),
            saveUserToFirestoreUseCase = get()
        )
    }
}