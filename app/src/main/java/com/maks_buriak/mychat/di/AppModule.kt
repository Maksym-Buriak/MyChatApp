package com.maks_buriak.mychat.di

import com.maks_buriak.mychat.R
import com.maks_buriak.mychat.data.authentication.google.GoogleSignInHelper
import com.maks_buriak.mychat.presentation.UserManager
import com.maks_buriak.mychat.presentation.viewmodel.AuthViewModel
import com.maks_buriak.mychat.presentation.viewmodel.MessageViewModel
import com.maks_buriak.mychat.presentation.viewmodel.PhoneAuthViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    single {
        GoogleSignInHelper(
            context = androidContext(),
            webClientId = androidContext().getString(R.string.default_web_client_id)
        )
    }

    // UserManager singleton for global user state
    single { UserManager(repository = get()) }

    viewModel<MessageViewModel> {
        MessageViewModel(
            sendMessageUseCase = get(),
            userManager = get()
        )
    }

    viewModel<AuthViewModel> {
        AuthViewModel(
            signInWithGoogleUseCase = get(),
            googleSignInHelper = get(),
            saveUserToFirestoreUseCase = get(),
            userManager = get()
        )
    }

    viewModel<PhoneAuthViewModel> {
        PhoneAuthViewModel(
            sendVerificationCodeUseCase = get(),
            verifyCodeUseCase = get(),
            getCurrentUserUseCase = get(),
            updateUserPhoneNumberUseCase = get(),
            isPhoneNumberTakenUseCase = get(),
            userManager = get()
        )
    }
}