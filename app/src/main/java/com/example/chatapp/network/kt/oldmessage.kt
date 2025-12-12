package com.example.chatapp.network

import retrofit2.http.GET
import retrofit2.http.Path


//data class ChatMessage(
//    val _id: String,
//    val senderId: String,
//    val receiverId: String,
//    val message: String,
//    val createdAt: String
//)

interface MessageApi {
    @GET("/api/messages/{senderId}/{receiverId}")
    suspend fun getMessages(
        @Path("senderId") senderId: String,
        @Path("receiverId") receiverId: String
    ): List<ChatMessage>
}
