package com.example.chatapp.user

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
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
import com.example.chatapp.network.FriendResponse
import com.example.chatapp.network.RetrofitClient

data class Chat(val name: String, val isonline: Boolean, val id: String )
@Composable
fun HomeScreen(navController: NavHostController) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    // Get userId from DataStore
    val userId by LoginDataStore.getId(context).collectAsState(initial = "")

    var friends by remember { mutableStateOf<List<FriendResponse>>(emptyList()) }
    var input by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(true) }

    // Fetch friends from backend
    LaunchedEffect(userId) {
        if (userId.isNotEmpty()) {
            try {
                friends = RetrofitClient.friendApi.getFriends(userId)
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(context, "Error fetching chats: ${e.message}", Toast.LENGTH_SHORT).show()
            } finally {
                isLoading = false
            }
        }
    }

    Scaffold(
        bottomBar = {
            NavigationBar(modifier = Modifier.height(60.dp)) {
                NavigationBarItem(
                    selected = false,
                    onClick = { navController.navigate("profile") },
                    icon = { Icon(Icons.Default.AccountCircle, contentDescription = "Profile") },
                    label = { Text("Profile") }
                )
                NavigationBarItem(
                    selected = true,
                    onClick = { /* Handle navigation */ },
                    icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
                    label = { Text("Home") }
                )
                NavigationBarItem(
                    selected = false,
                    onClick = { navController.navigate("notification") },
                    icon = { Icon(Icons.Default.Notifications, contentDescription = "Notifications") },
                    label = { Text("Notifications") }
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
                .padding(paddingValues)
        ) {
            // Header
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text(
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .padding(start = 16.dp),
                    text = "My Chats",
                    fontSize = 30.sp,
                    color = Color.White
                )
            }

            // Search Bar
            OutlinedTextField(
                value = input,
                onValueChange = { input = it },
                placeholder = { Text("Search") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .padding(horizontal = 16.dp, vertical = 0.dp),
                shape = RoundedCornerShape(20.dp),
            )

            // Active Users
            LazyRow(
                modifier = Modifier
                    .height(100.dp)
                    .padding(vertical = 8.dp),
                contentPadding = PaddingValues(horizontal = 20.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(friends) { user ->
                    ActiveUserItem(user = Chat(user.name,user.isonline, user.id), navController = navController)
                }
            }

            // Chat List
            if (isLoading) {
                Text(
                    text = "Loading...",
                    color = Color.White,
                    modifier = Modifier.padding(16.dp)
                )
            } else if (friends.isEmpty()) {
                Text(
                    text = "No friends found",
                    color = Color.White,
                    modifier = Modifier.padding(16.dp)
                )
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(vertical = 8.dp)
                ) {
                    val filteredFriends = friends.filter {
                        it.name.contains(input, ignoreCase = true)
                    }

                    items(filteredFriends) { friend ->
                        ChatItem(
                            chat = Chat(friend.name,friend.isonline, friend.id),
                            navController = navController
                        )
                    }
                }
            }
        }
    }
}
