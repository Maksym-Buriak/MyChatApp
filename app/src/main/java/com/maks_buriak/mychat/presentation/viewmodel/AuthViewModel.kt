package com.maks_buriak.mychat.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maks_buriak.mychat.domain.models.User
import com.maks_buriak.mychat.domain.repository.FirebaseAuthRepository
import com.maks_buriak.mychat.domain.usecase.SignInWithGoogleUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthViewModel(
    private val signInWithGoogleUseCase: SignInWithGoogleUseCase,
    private val authRepository: FirebaseAuthRepository
) : ViewModel() {

    private val _userState = MutableStateFlow<User?>(authRepository.getCurrentUser())
    val userState: StateFlow<User?> = _userState

    fun onGoogleSignIn(idToken: String) {
        viewModelScope.launch {
            val result = signInWithGoogleUseCase(idToken)
            result.onSuccess { user ->
                _userState.value = user
            }.onFailure {
                _userState.value = null
            }
        }
    }
}