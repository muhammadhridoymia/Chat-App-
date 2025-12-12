package com.example.chatapp.network

import okhttp3.Response
import retrofit2.http.GET
import retrofit2.http.Path


data class user(
    val _id: String,
    val name: String,
    val email: String,
    val isonline: Boolean,
    val profilePic: String? // Optional since it might be null/empty
)

data class SingleUserResponse(
    val user: user
)

// ApiService.kt
interface findfrined {
    @GET("api/users/find/{email}")
    suspend fun findUserByEmail(@Path("email") email: String): SingleUserResponse

}