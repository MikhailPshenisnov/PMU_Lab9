package com.example.wishlistapp.api.models.requests

data class LoginUserRequest(
    val login: String?,
    val password: String?
)