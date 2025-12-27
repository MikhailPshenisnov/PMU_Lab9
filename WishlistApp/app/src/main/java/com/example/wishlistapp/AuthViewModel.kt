package com.example.wishlistapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wishlistapp.repository.AuthRepository
import com.example.wishlistapp.ui.state.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {

    private val repository = AuthRepository()

    private val _authState =
        MutableStateFlow<UiState<String?>>(UiState.Idle)
    val authState: StateFlow<UiState<String?>> = _authState

    fun register(login: String, password: String) {
        viewModelScope.launch {
            _authState.value = UiState.Loading

            val response = repository.register(login, password)

            if (response.isSuccessful) {
                _authState.value =
                    UiState.Success(response.body()?.userId)
            } else {
                _authState.value =
                    UiState.Error("Registration failed")
            }
        }
    }

    fun login(login: String, password: String) {
        viewModelScope.launch {
            _authState.value = UiState.Loading

            val response = repository.login(login, password)

            if (response.isSuccessful) {
                _authState.value =
                    UiState.Success(response.body()?.userId)
            } else {
                _authState.value =
                    UiState.Error("Login failed")
            }
        }
    }
}