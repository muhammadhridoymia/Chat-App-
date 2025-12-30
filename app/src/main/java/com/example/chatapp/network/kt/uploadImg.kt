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
    uris: List<Uri>,
    userId: String,
    targetId: String,
    isGroup: Boolean,
    context: Context,
    message: String
) {
    try {
        val parts = uris.mapIndexed { index, uri ->
            val inputStream = context.contentResolver.openInputStream(uri)
            val tempFile = File(context.cacheDir, "upload_$index.jpg")
            inputStream?.use { input ->
                tempFile.outputStream().use { output ->
                    input.copyTo(output)
                }
            }
            val requestFile = tempFile.asRequestBody("image/jpeg".toMediaTypeOrNull())
            MultipartBody.Part.createFormData("images", tempFile.name, requestFile)
        }

        val res = RetrofitClient.uploadImage.uploadImage(parts)
        val imageUrls = res.urls

        if (isGroup) {
            SocketManager.onGroupSendMessage(userId, targetId, message, imageUrls)
        } else {
            SocketManager.sendMessage(userId, targetId, message, imageUrls)
        }

    } catch (e: Exception) {
        Toast.makeText(
            context,
            "Image send failed:\n${e.message}",
            Toast.LENGTH_LONG
        ).show()
    }
}
