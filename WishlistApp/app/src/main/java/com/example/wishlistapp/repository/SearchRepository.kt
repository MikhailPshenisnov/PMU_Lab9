package com.example.wishlistapp.repository

import com.example.wishlistapp.api.RetrofitClient
import com.example.wishlistapp.api.models.ItemModel
import retrofit2.Response

class SearchRepository {

    suspend fun getItemsByUserId(userId: String): Response<List<ItemModel>> {
        return try {
            val response = RetrofitClient.apiService.getAllItems()
            if (response.isSuccessful) {
                val items = response.body()?.items?.filter { it.userId == userId } ?: emptyList()
                Response.success(items)
            } else {
                Response.error(response.code(), response.errorBody()!!)
            }
        } catch (e: Exception) {
            Response.error(
                500,
                okhttp3.ResponseBody.create(null, "Network error")
            )
        }
    }
}
