package com.example.chatapp.user

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chatapp.LoginDataStore
import com.example.chatapp.network.ChatMessage
import com.example.chatapp.network.RetrofitClient
import com.example.chatapp.network.SocketManager

@Composable
fun MessagePage(name: String, id: String, isonline: Boolean) {

    val context = LocalContext.current
    val userId by LoginDataStore.getId(context).collectAsState(initial = "")

    var messageText by remember { mutableStateOf("") }
    var messages by remember { mutableStateOf<List<ChatMessage>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }

    val listState = rememberLazyListState()

    // 🔹 Load old messages + init socket (ONLY ONCE)
    LaunchedEffect(userId) {
        if (userId.isNotEmpty()) {
            try {
                messages = RetrofitClient.oldmessages.getMessages(userId, id)

                SocketManager.initSocket()
                SocketManager.joinRoom(userId)

                SocketManager.onMessageReceived { newMessage ->
                    messages = messages + newMessage
                }

            } catch (e: Exception) {
                Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
            } finally {
                isLoading = false
            }
        }
    }

    // 🔹 Auto scroll to last message
    LaunchedEffect(messages.size) {
        if (messages.isNotEmpty()) {
            listState.animateScrollToItem(messages.size - 1)
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            SocketManager.disconnect()
        }
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {

            // 🔹 TOP HEADER
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Blue)
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                IconButton(onClick = { }) {
                    Icon(Icons.Default.ArrowBackIosNew, contentDescription = "Back", tint = Color.White)
                }

                Spacer(modifier = Modifier.width(8.dp))

                Box(
                    modifier = Modifier
                        .size(45.dp)
                        .background(Color.Gray, shape = RoundedCornerShape(50))
                )

                Spacer(modifier = Modifier.width(10.dp))

                Column {
                    Text(name, color = Color.White, fontSize = 18.sp)
                    Text(
                        if (isonline) "Online" else "Offline",
                        color = if (isonline) Color.Green else Color.Red,
                        fontSize = 12.sp
                    )
                }
            }

            // 🔹 CHAT MESSAGES
            LazyColumn(
                state = listState,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(10.dp)
            ) {
                items(messages) { msg ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement =
                            if (msg.senderId == userId) Arrangement.End else Arrangement.Start
                    ) {
                        Box(
                            modifier = Modifier
                                .background(
                                    if (msg.senderId == userId) Color.Blue else Color.Gray,
                                    shape = RoundedCornerShape(12.dp)
                                )
                                .padding(10.dp)
                        ) {
                            Text(msg.message, color = Color.White)
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }

            // 🔹 BOTTOM BAR
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .background(Color.Black)
                    .padding(horizontal = 6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                IconButton(onClick = {}) {
                    Icon(Icons.Default.Photo, contentDescription = "Photo", tint = Color.White)
                }
                IconButton(onClick = {}) {
                    Icon(Icons.Default.CameraAlt, contentDescription = "Camera", tint = Color.White)
                }
                IconButton(onClick = {}) {
                    Icon(Icons.Default.Mic, contentDescription = "Mic", tint = Color.White)
                }

                TextField(
                    value = messageText,
                    onValueChange = { messageText = it },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp),
                    maxLines = 1
                )

                IconButton(onClick = {
                    if (messageText.isNotBlank()) {
                        SocketManager.sendMessage(userId, id, messageText)
                        messageText = ""
                    }
                }) {
                    Icon(Icons.Default.Send, contentDescription = "Send", tint = Color.White)
                }
            }
        }
    }
}
