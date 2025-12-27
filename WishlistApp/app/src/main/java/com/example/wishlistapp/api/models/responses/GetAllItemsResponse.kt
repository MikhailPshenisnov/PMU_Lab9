package com.example.wishlistapp.api.models.responses

import com.example.wishlistapp.api.models.ItemModel

data class GetAllItemsResponse(
    val items: List<ItemModel>?
)