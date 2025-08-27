package com.maks_buriak.mychat.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maks_buriak.mychat.domain.usecase.IsNickNameTakenUseCase
import com.maks_buriak.mychat.domain.usecase.UpdateUserNickUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class NickNameViewModel(
    private val updateUserNickUseCase: UpdateUserNickUseCase,
    private val isNickNameTakenUseCase: IsNickNameTakenUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<NickNameUiState>(NickNameUiState.Idle)
    val uiState: StateFlow<NickNameUiState> = _uiState

    fun saveNickName(uid: String, nickName: String) {
        viewModelScope.launch {
            _uiState.value = NickNameUiState.Loading
            val taken = isNickNameTakenUseCase(nickName)
            if (taken) {
                _uiState.value = NickNameUiState.Error("Цей нік вже зайнятий")
            } else {
                updateUserNickUseCase(uid, nickName)
                _uiState.value = NickNameUiState.Success
            }
        }
    }
}

sealed class NickNameUiState {
    object Idle : NickNameUiState()
    object Loading : NickNameUiState()
    object Success : NickNameUiState()
    data class Error(val message: String) : NickNameUiState()
}