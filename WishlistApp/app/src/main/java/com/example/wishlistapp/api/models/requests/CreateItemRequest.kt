package com.example.wishlistapp.api.models.requests

data class CreateItemRequest(
    val userId: String,
    val title: String?,
    val price: Double,
    val link: String?
)