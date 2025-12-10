package  com.example.chatapp.network

import com.google.gson.annotations.SerializedName
import retrofit2.http.GET
import retrofit2.http.Path


data class FriendResponse(
    @SerializedName("_id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("isonline") val isonline: Boolean = false
)

interface ApiServices {
    @GET("/api/users/friends/{userId}")
    suspend fun getFriends(@Path("userId") userId: String): List<FriendResponse>
}
