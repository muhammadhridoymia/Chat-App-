package com.example.chatapp.user

import VoiceMessage
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.navigation.NavHostController
import com.example.chatapp.LoginDataStore
import com.example.chatapp.network.ChatMessage
import com.example.chatapp.network.RetrofitClient
import com.example.chatapp.network.SocketManager
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.ui.layout.ContentScale
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import org.jetbrains.annotations.Async
import uploadAndSendImage


@Composable
fun MessagePage(navController: NavHostController, name: String, id: String, isonline: Boolean, isGroup: Boolean) {

    val context = LocalContext.current
    val userId by LoginDataStore.getId(context).collectAsState(initial = "")

    var messageText by remember { mutableStateOf("") }
    var messages by remember { mutableStateOf<List<ChatMessage>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var istyping by remember { mutableStateOf(false) }
    var isUserTyping by remember { mutableStateOf(false) }
    var typingJob by remember { mutableStateOf<Job?>(null) }
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }

    val scope = rememberCoroutineScope()




    val listState = rememberLazyListState()

    // 🔹 Load old messages
    LaunchedEffect(userId) {
        if (userId.isNotEmpty()) {
            try {
                if (isGroup){
                    messages= RetrofitClient.oldgroupmessages.getoldgroupmessage(id)
                }else{
                    messages = RetrofitClient.oldmessages.getMessages(userId, id)

                }

                SocketManager.onMessageReceived { newMessage ->
                    messages = messages + newMessage
                }
                SocketManager.onGroupMessageReceived { newMessage ->
                    messages = messages + newMessage
                }
                SocketManager.onTyping { isTyping ->
                    istyping = isTyping.isTyping
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
            SocketManager.socket.off("typing")
            SocketManager.disconnect()
        }
    }

    //Image piker
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        if (uri != null) {
            selectedImageUri = uri  // ✅ Now it works
            scope.launch {
                uploadAndSendImage(uri, userId, id, isGroup, context, messageText,)
            }
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

                IconButton(onClick = {navController.navigate("home")}) {
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
                modifier = Modifier.weight(1f).padding(10.dp),
                state = listState
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
                                    RoundedCornerShape(12.dp)
                                )
                                .padding(10.dp)
                                .widthIn(max = 260.dp)
                        ) {

                            Column {

                                // 🔹 TEXT
                                msg.message?.let {
                                    Text(it, color = Color.White)
                                    Spacer(Modifier.height(4.dp))
                                }

                                // 🔹 MULTIPLE IMAGES
                                if (msg.img.isNotEmpty()) {
                                    LazyRow {
                                        items(msg.img) { image ->
                                            AsyncImage(
                                                model = image,
                                                contentDescription = null,
                                                modifier = Modifier
                                                    .size(120.dp)
                                                    .padding(4.dp)
                                                    .background(
                                                        Color.Black,
                                                        RoundedCornerShape(8.dp)
                                                    ),
                                                contentScale = ContentScale.Crop
                                            )
                                        }
                                    }
                                }

                                // 🔹 VOICE
                                msg.voice?.let { voiceUrl ->
                                    VoiceMessage(url = voiceUrl)
                                    Spacer(Modifier.height(4.dp))
                                }


                                // 🔹 SEEN
                                if (msg.senderId == userId) {
                                    Text(
                                        if (msg.seen) "✔✔ Seen" else "✔ Sent",
                                        fontSize = 10.sp,
                                        color = Color.White,
                                        modifier = Modifier.align(Alignment.End)
                                    )
                                }
                            }
                        }
                    }
                    Spacer(Modifier.height(8.dp))
                }
            }
            if (istyping) {
                Text(
                    text = "$name is typing...",
                    color = Color.Green,
                    fontSize = 12.sp,
                    modifier = Modifier
                        .padding(start = 10.dp, bottom = 4.dp)
                )
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

                IconButton(onClick = {launcher.launch("image/*")}) {
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
                    onValueChange = { text ->
                        SocketManager.handleTyping(
                            text = text,
                            userId = userId,
                            targetId = id,
                            isGroup = isGroup,
                            scope = scope,
                            typingJobState = mutableStateOf(typingJob),
                            isUserTypingState = mutableStateOf(isUserTyping)
                        ) {

                            messageText = it
                        }
                    },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp),
                    maxLines = 1
                )


                IconButton(onClick = {
                    if (messageText.isNotBlank()) {
                        if(isGroup){
                            SocketManager.onGroupSendMessage(userId, id, messageText)
                            messageText = ""
                        }else{
                            SocketManager.sendMessage(userId, id, messageText)
                            messageText = ""
                        }
                    }
                }) {
                    Icon(Icons.Default.Send, contentDescription = "Send", tint = Color.White)
                }
            }
        }
    }
}