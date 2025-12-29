package com.example.chatapp.network

import androidx.compose.runtime.MutableState
import io.socket.client.IO
import io.socket.client.Socket
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONObject
import java.lang.Exception



data class TypingStatus(
    val isGroupChat: Boolean,
    val senderId: String,
    val receiverId: String? = null,
    val groupId: String? = null,
    val isTyping: Boolean,
)

data class ChatMessage(
    val senderId: String,
    val receiverId: String? =null,
    val message: String? = null,
    val createdAt: String? =null,
    val groupId: String? = null,
    val seen: Boolean,
    val img: List<String> = emptyList(),
    val voice: String? = null
)

object SocketManager {
    private const val SOCKET_URL = "http://172.172.4.243:5000"

    lateinit var socket: Socket

    fun initSocket() {
        socket = IO.socket(SOCKET_URL)
        socket.connect()
    }

    fun disconnect() {
        if (this::socket.isInitialized && socket.connected()) {
            socket.disconnect()
        }
    }

    fun joinRoom(userId: String) {
        socket.emit("joinRoom", userId)
    }

fun sendMessage(senderId: String, receiverId: String, message: String,img: List<String>? = null) {
    try {
        val data = JSONObject().apply {
            put("senderId", senderId)
            put("receiverId", receiverId)
            put("message", message)
            img?.let { put("img", JSONArray(it)) }
        }
        socket.emit("sendMessage", data)
    } catch (e: Exception) {
        e.printStackTrace()
    }
}


    fun onGroupSendMessage(senderId: String, groupId: String, message: String, img: List<String>? = null,) {
        try {
            val data = JSONObject().apply {
                put("senderId", senderId)
                put("groupId", groupId)
                put("message", message)
                img?.let { put("img", JSONArray(it)) }
            }
            socket.emit("sendGroupMessage", data)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    fun onMessageReceived(callback: (ChatMessage) -> Unit) {
        socket.on("receiveMessage") { args ->
            try {
                if (args.isNotEmpty()) {
                    val json = args[0] as JSONObject
                    val imgList = mutableListOf<String>()
                    if (json.has("img") && !json.isNull("img")) {
                        val jsonArray = json.optJSONArray("img")
                        if (jsonArray != null) {
                            for (i in 0 until jsonArray.length()) {
                                imgList.add(jsonArray.getString(i))
                            }
                        } else {
                            val singleImg = json.optString("img", "")
                            if (singleImg.isNotEmpty()) imgList.add(singleImg)
                        }
                    }

                    val msg = ChatMessage(
                        senderId = json.getString("senderId"),
                        receiverId = json.optString("receiverId", null),
                        message = json.getString("message"),
                        img = imgList,
                        voice = json.optString("voice", null),
                        createdAt = json.optString("createdAt", null),
                        seen = json.optBoolean("seen", false)
                    )
                    callback(msg)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }


    fun onGroupMessageReceived(callback: (ChatMessage) -> Unit) {
        socket.on("receiveGroupMessage") { args ->
            if (args.isNotEmpty()) {
                val json = args[0] as JSONObject
                val imgList = mutableListOf<String>()
                if (json.has("img") && !json.isNull("img")) {
                    val jsonArray = json.optJSONArray("img")
                    if (jsonArray != null) {
                        for (i in 0 until jsonArray.length()) {
                            imgList.add(jsonArray.getString(i))
                        }
                    } else {
                        val singleImg = json.optString("img", "")
                        if (singleImg.isNotEmpty()) imgList.add(singleImg)
                    }
                }
                val msg = ChatMessage(
                    senderId = json.getString("senderId"),
                    groupId =json.getString("groupId"),
                    message = json.getString("message"),
                    img = imgList,
                    createdAt = json.getString("createdAt")
                    , seen = json.getBoolean("seen")
                )
                callback(msg)
            }
        }
    }


    // Typing indicator listener
    fun onTyping(callback: (TypingStatus) -> Unit) {
        socket.on("typing") { args ->
            if (args.isNotEmpty()) {
                val json = args[0] as JSONObject
                val msg = TypingStatus(
                    isGroupChat = json.getBoolean("isGroupChat"),
                    senderId = json.getString("senderId"),
                    receiverId = json.optString("receiverId", null),
                    groupId = json.optString("groupId", null),
                    isTyping = json.getBoolean("isTyping")
                )
                callback(msg)
            }
        }
    }

    fun startTyping(isGroupChat: Boolean, senderId: String, receiverId: String? = null, groupId: String? = null, isTyping: Boolean) {
        try {
            val data = JSONObject().apply {
                put("isGroupChat", isGroupChat)
                put("senderId", senderId)
                put("receiverId", receiverId)
                put("groupId", groupId)
                put("isTyping", isTyping)
            }
            socket.emit("typing", data)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }


// handler ,sending typing status to server
    fun handleTyping(
        text: String,
        userId: String,
        targetId: String,
        isGroup: Boolean,
        scope: CoroutineScope,
        typingJobState: MutableState<Job?>,
        isUserTypingState: MutableState<Boolean>,
        onTextChange: (String) -> Unit
    ) {
        onTextChange(text)

        // Start typing
        if (!isUserTypingState.value) {
            isUserTypingState.value = true
            startTyping(
                groupId = targetId,
                senderId = userId,
                receiverId = targetId,
                isTyping = true,
                isGroupChat = isGroup
            )
        }

        // Stop typing (debounced)
        typingJobState.value?.cancel()
        typingJobState.value = scope.launch {
            delay(2000)
            isUserTypingState.value = false
            startTyping(
                groupId = targetId,
                senderId = userId,
                receiverId = targetId,
                isTyping = false,
                isGroupChat = isGroup
            )
        }
    }

}//
