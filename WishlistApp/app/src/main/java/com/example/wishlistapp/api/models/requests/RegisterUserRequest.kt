package com.example.wishlistapp.api.models.requests

data class RegisterUserRequest(
    val login: String?,
    val password: String?
)