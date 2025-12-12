package com.example.chatapp.network

import io.socket.client.IO
import io.socket.client.Socket
import org.json.JSONObject

data class ChatMessage(
    val senderId: String,
    val receiverId: String,
    val message: String,
    val createdAt: String
)

object SocketManager {
    private const val SOCKET_URL = "http://172.172.5.238:5000"

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

fun sendMessage(senderId: String, receiverId: String, message: String) {
    try {
        val data = JSONObject().apply {
            put("senderId", senderId)
            put("receiverId", receiverId)
            put("message", message)
        }
        socket.emit("sendMessage", data)
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

    fun onMessageReceived(callback: (ChatMessage) -> Unit) {
        socket.on("receiveMessage") { args ->
            if (args.isNotEmpty()) {
                val json = args[0] as JSONObject
                val msg = ChatMessage(
                    senderId = json.getString("senderId"),
                    receiverId = json.getString("receiverId"),
                    message = json.getString("message"),
                    createdAt = json.getString("createdAt")
                )
                callback(msg)
            }
        }
    }
}
