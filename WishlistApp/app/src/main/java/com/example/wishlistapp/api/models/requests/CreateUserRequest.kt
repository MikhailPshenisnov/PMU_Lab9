package com.example.wishlistapp.api.models.requests

data class CreateUserRequest(
    val login: String?,
    val password: String?
)