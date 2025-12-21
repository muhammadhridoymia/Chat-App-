package  com.example.chatapp.network

import retrofit2.http.GET
import retrofit2.http.Path


data class GroupResponse(
    val _id: String,
    val name: String,
    val members: List<String>,
    val admins: List<String>,
    val createdBy: String
)

interface GroupApi {
    @GET("/api/users/my/groups/{userId}")
    suspend fun getMyGroups(@Path("userId") userId: String): List<GroupResponse>
}
