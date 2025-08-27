package com.maks_buriak.mychat.presentation.viewmodel

import android.content.Intent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.common.api.ApiException
import com.maks_buriak.mychat.data.authentication.google.GoogleSignInHelper
import com.maks_buriak.mychat.domain.models.User
import com.maks_buriak.mychat.domain.usecase.GetUserByUidUseCase
import com.maks_buriak.mychat.domain.usecase.SaveUserToFirestoreUseCase
import com.maks_buriak.mychat.domain.usecase.SignInWithGoogleUseCase
import com.maks_buriak.mychat.presentation.UserManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthViewModel(
    private val signInWithGoogleUseCase: SignInWithGoogleUseCase,
    private val googleSignInHelper: GoogleSignInHelper,
    private val saveUserToFirestoreUseCase: SaveUserToFirestoreUseCase,
    private val userManager: UserManager,
    private val getUserByUidUseCase: GetUserByUidUseCase
) : ViewModel() {

    val userState: StateFlow<User?> = userManager.currentUser

    private val _nickState = MutableStateFlow<String?>(null)
    val nickState: StateFlow<String?> = _nickState

    private var nickLoadedForUid: String? = null // щоб не робити повторний запит

    fun startGoogleSignIn(): Intent {
        return googleSignInHelper.getSignInIntent()
    }

    fun handleGoogleSignInResult(data: Intent?) {
        val task = googleSignInHelper.getSignedInAccountFromIntent(data)
        try {
            val account = task.getResult(ApiException::class.java)
            account?.idToken?.let { idToken ->
                onGoogleSignIn(idToken)
            }
        } catch (e: ApiException) {
            e.printStackTrace()
        }
    }


    private fun onGoogleSignIn(idToken: String) {
        viewModelScope.launch {
            val result = signInWithGoogleUseCase(idToken)
            result.onSuccess { authUserResult ->

                val user = authUserResult.user

                if (authUserResult.isNewUser) {
                    saveUserToFirestoreUseCase(user)
                }

                userManager.refreshUser()

                val userFromFirestore = getUserByUidUseCase(user.uid)
                _nickState.value = userFromFirestore?.nickName
            }.onFailure {
                //_userState.value = null
                it.printStackTrace()
            }
        }
    }


    fun loadNickNameIfNeeded(uid: String) {
        if (nickLoadedForUid == uid) return

        viewModelScope.launch {
            try {
                val user = getUserByUidUseCase(uid)
                // Якщо нік відсутній у Firestore, ставимо пустий рядок, а не null
                _nickState.value = user?.nickName ?: ""
                nickLoadedForUid = uid // запам'ятовуємо, що вже завантажили
            } catch (e: Exception) {
                e.printStackTrace()
                _nickState.value = "" // щоб не застрягати в Loading
                nickLoadedForUid = uid
            }
        }
    }
}