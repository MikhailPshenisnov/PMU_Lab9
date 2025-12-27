package com.example.wishlistapp.repository

import com.example.wishlistapp.api.models.requests.*
import com.example.wishlistapp.api.RetrofitClient

class AuthRepository {
    private val api = RetrofitClient.apiService

    suspend fun register(login: String, password: String) =
        api.registerUser(
            RegisterUserRequest(
                login = login,
                password = password
            )
        )

    suspend fun login(login: String, password: String) =
        api.loginUser(
            LoginUserRequest(
                login = login,
                password = password
            )
        )
}