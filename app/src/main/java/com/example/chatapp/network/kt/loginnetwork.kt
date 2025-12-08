package com.example.chatapp.network

import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


data class LoginRequest(
    val email: String,
    val password: String
)
data class LoginResponse(
    val success: Boolean,
    val message: String
)
interface ApiService {
    @POST("/api/users/login")
    suspend fun login(@Body request: LoginRequest): LoginResponse
}

object RetrofitClient {
    val api: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl("http://192.168.0.107:5000/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}