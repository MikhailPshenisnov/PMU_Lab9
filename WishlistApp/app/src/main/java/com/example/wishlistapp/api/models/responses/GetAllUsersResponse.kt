package com.example.wishlistapp.api.models.responses

import com.example.wishlistapp.api.models.UserModel

data class GetAllUsersResponse(
    val users: List<UserModel>?
)