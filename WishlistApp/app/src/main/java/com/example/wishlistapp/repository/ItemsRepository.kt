package com.example.wishlistapp.repository

import com.example.wishlistapp.api.models.requests.*
import com.example.wishlistapp.api.RetrofitClient

class ItemsRepository {
    private val api = RetrofitClient.apiService

    suspend fun getAllItems() =
        api.getAllItems()

    suspend fun getItem(itemId: String) =
        api.getItem(itemId)

    suspend fun createItem(
        userId: String,
        title: String,
        price: Double,
        link: String
    ) =
        api.createItem(
            CreateItemRequest(
                userId = userId,
                title = title,
                price = price,
                link = link
            )
        )

    suspend fun updateItem(
        itemId: String,
        userId: String,
        title: String,
        price: Double,
        link: String
    ) =
        api.updateItem(
            itemId,
            UpdateItemRequest(
                userId = userId,
                title = title,
                price = price,
                link = link
            )
        )

    suspend fun deleteItem(itemId: String) =
        api.deleteItem(itemId)
}
