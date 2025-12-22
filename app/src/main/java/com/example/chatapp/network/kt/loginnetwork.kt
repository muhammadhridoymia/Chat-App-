package com.example.chatapp.network

import com.google.gson.annotations.SerializedName
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


data class LoginRequest(
    val email: String,
    val password: String
)
data class User(
    @SerializedName("_id") val id: String,
    val name: String,
    val email: String
)

data class LoginResponse(
    val success: Boolean,
    val message: String,
    val user: User?
)

interface ApiService {
    @POST("/api/users/login")
    suspend fun login(@Body request: LoginRequest): LoginResponse
}


object RetrofitClient {
    private const val BASE_URL = "http://172.172.7.251:5000/"

    val api: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }


    val friendApi: ApiServices by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiServices::class.java)
    }

    val oldmessages: MessageApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MessageApi::class.java)
    }

    val findfrineds: findfrined by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(findfrined::class.java)
    }
    val sendFriendReqs: sendFriendReq by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(sendFriendReq::class.java)
    }

    val fetchFriendReqs: fetchFriends by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(fetchFriends::class.java)
    }
    val groupApi: GroupApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GroupApi::class.java)
    }
    val oldgroupmessages: oldmessageapi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(oldmessageapi::class.java)
    }

}



