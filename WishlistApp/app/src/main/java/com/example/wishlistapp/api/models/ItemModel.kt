package com.example.wishlistapp.api.models

data class ItemModel(
    val id: String,
    val userId: String,
    val title: String?,
    val price: Double,
    val link: String?
)