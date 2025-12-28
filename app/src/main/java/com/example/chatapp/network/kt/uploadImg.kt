import android.content.Context
import android.net.Uri
import android.widget.Toast
import com.example.chatapp.network.RetrofitClient
import com.example.chatapp.network.SocketManager
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

suspend fun uploadAndSendImage(
    uri: Uri,
    userId: String,
    targetId: String,
    isGroup: Boolean,
    context: Context,
    message: String
) {
    try {
        val inputStream = context.contentResolver.openInputStream(uri)
        val tempFile = File(context.cacheDir, "upload.jpg")
        inputStream?.use { input ->
            tempFile.outputStream().use { output ->
                input.copyTo(output)
            }
        }

        val requestFile = tempFile.asRequestBody("image/jpeg".toMediaTypeOrNull())
        val body = MultipartBody.Part.createFormData("images", tempFile.name, requestFile)

        // Call Retrofit to upload
        val imageUrl = RetrofitClient.uploadImage.uploadImage(body)

        // Send image via Socket
        if (isGroup) {
            SocketManager.onGroupSendMessage(userId, targetId, message, listOf(imageUrl))
        } else {
            SocketManager.sendMessage(userId, targetId, imageUrl)
        }

    } catch (e: Exception) {
        Toast.makeText(context, "Image send failed: ${e.message}", Toast.LENGTH_SHORT).show()
    }
}
