package com.example.chatapp.network

import com.google.gson.annotations.SerializedName
import retrofit2.http.GET
import retrofit2.http.Path



data class FromUser(
    @SerializedName("_id") val id: String,
    val name: String,
    val email: String,
    val profilePic: String?
)
data class FriendRequestResponse(
    @SerializedName("_id") val id: String,
    val fromUser: FromUser
)

interface fetchFriends {
    @GET("/api/users/load/friend/request/{userId}")
    suspend fun getFriendReq(@Path("userId") userId: String): List<FriendRequestResponse>
}