package com.example.wishlistapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wishlistapp.api.models.UserModel
import com.example.wishlistapp.repository.UsersRepository
import com.example.wishlistapp.ui.state.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UsersViewModel : ViewModel() {

    private val repository = UsersRepository()

    private val _usersState =
        MutableStateFlow<UiState<List<UserModel>>>(UiState.Idle)
    val usersState: StateFlow<UiState<List<UserModel>>> = _usersState

    private val _userState =
        MutableStateFlow<UiState<UserModel>>(UiState.Idle)
    val userState: StateFlow<UiState<UserModel>> = _userState

    private val _actionState =
        MutableStateFlow<UiState<String>>(UiState.Idle)
    val actionState: StateFlow<UiState<String>> = _actionState

    fun loadUsers() {
        viewModelScope.launch {
            _usersState.value = UiState.Loading

            val response = repository.getAllUsers()

            if (response.isSuccessful) {
                _usersState.value =
                    UiState.Success(response.body()?.users ?: emptyList())
            } else {
                _usersState.value =
                    UiState.Error("Failed to load users")
            }
        }
    }

    fun loadUser(userId: String) {
        viewModelScope.launch {
            _userState.value = UiState.Loading

            val response = repository.getUser(userId)

            if (response.isSuccessful) {
                _userState.value =
                    UiState.Success(response.body()!!.user)
            } else {
                _userState.value =
                    UiState.Error("Failed to load user")
            }
        }
    }

    fun createUser(login: String, password: String) {
        viewModelScope.launch {
            _actionState.value = UiState.Loading

            val response = repository.createUser(login, password)

            if (response.isSuccessful) {
                _actionState.value =
                    UiState.Success(response.body()!!.userId)
            } else {
                _actionState.value =
                    UiState.Error("Failed to create user")
            }
        }
    }
}