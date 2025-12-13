package com.example.chatapp.network

import retrofit2.http.Body
import retrofit2.http.POST



data class  responseFriendReq(
    val message: String,
)

data class sendfriendReq(
    val fromId: String,
    val toId: String
)

interface sendFriendReq {
    @POST("api/users/friend/request")
    suspend fun sendRequest(@Body request: sendfriendReq):responseFriendReq
}