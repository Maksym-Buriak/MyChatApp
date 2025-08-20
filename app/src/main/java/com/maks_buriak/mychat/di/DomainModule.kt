package com.maks_buriak.mychat.di

import com.maks_buriak.mychat.domain.usecase.GetCurrentUserUseCase
import com.maks_buriak.mychat.domain.usecase.IsPhoneNumberTakenUseCase
import com.maks_buriak.mychat.domain.usecase.SaveUserToFirestoreUseCase
import com.maks_buriak.mychat.domain.usecase.SendMessageUseCase
import com.maks_buriak.mychat.domain.usecase.SendVerificationCodeUseCase
import com.maks_buriak.mychat.domain.usecase.SignInWithGoogleUseCase
import com.maks_buriak.mychat.domain.usecase.SignOutUseCase
import com.maks_buriak.mychat.domain.usecase.UpdateUserPhoneNumberUseCase
import com.maks_buriak.mychat.domain.usecase.VerifyCodeUseCase
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

    factory<SaveUserToFirestoreUseCase> {
        SaveUserToFirestoreUseCase(repository = get())
    }

    factory<SendVerificationCodeUseCase> {
        SendVerificationCodeUseCase(repository = get())
    }

    factory<VerifyCodeUseCase> {
        VerifyCodeUseCase(repository = get())
    }

    factory<UpdateUserPhoneNumberUseCase> {
        UpdateUserPhoneNumberUseCase(repository = get())
    }

    factory<IsPhoneNumberTakenUseCase> {
        IsPhoneNumberTakenUseCase(repository = get())
    }
}