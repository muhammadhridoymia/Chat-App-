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
data class OldGroupResponse(
    val groupId: String,
    val senderId: String,
    val message: String,
)
interface oldmessageapi{
    @GET("/api/users/old/group/messages/{groupId}")
    suspend fun getoldgroupmessage(@Path("groupId") groupId: String): List<ChatMessage>
}
