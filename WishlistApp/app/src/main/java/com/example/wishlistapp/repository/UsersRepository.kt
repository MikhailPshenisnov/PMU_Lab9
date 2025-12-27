package com.example.wishlistapp.repository

import com.example.wishlistapp.api.models.requests.*
import com.example.wishlistapp.api.RetrofitClient

class UsersRepository {
    private val api = RetrofitClient.apiService

    suspend fun getAllUsers() =
        api.getAllUsers()

    suspend fun getUser(userId: String) =
        api.getUser(userId)

    suspend fun createUser(login: String, password: String) =
        api.createUser(
            CreateUserRequest(
                login = login,
                password = password
            )
        )
}