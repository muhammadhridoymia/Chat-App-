package com.example.chatapp.user

import androidx.compose.foundation.background
import androidx.navigation.NavHostController
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp



data class ActiveUser(
    val name: String
)
val activeUsers = listOf(
    ActiveUser("Alice"),
    ActiveUser("Bob"),
    ActiveUser("Charlie"),
    ActiveUser("David"),
    ActiveUser("Eva"),
    ActiveUser("Charlie"),
    ActiveUser("David"),
    ActiveUser("Eva")
)

data class Chat(
    val name: String,
    val lastMessage: String
)

@Composable
fun HomeScreen(navController: NavHostController) {
    var input by remember { mutableStateOf("") }

    // Example chat list
    val chats = listOf(
        Chat("Hridoy", "Hey! How are you?"),
        Chat("Riaz", "Let's meet tomorrow."),
        Chat("Sabbir", "Did you see the news?"),
        Chat("Shadhin", "Call me when free."),
        Chat("Rakib", "Good night!"),
        Chat("Alice", "Hey! How are you?"),
        Chat("Bob", "Let's meet tomorrow."),
        Chat("Charlie", "Did you see the news?"),
        Chat("David", "Call me when free."),
        Chat("Eva", "Good night!"),
    )

    Scaffold(
        bottomBar = {
            NavigationBar(
                modifier = Modifier
                    .height(60.dp),
            ) {
                NavigationBarItem(
                    selected = false,
                    onClick = { navController.navigate("profile") },
                    icon = { Icon(Icons.Default.Person, contentDescription = "Home") },
                    label = { Text("Profile") }
                )
                NavigationBarItem(
                    selected = true,
                    onClick = { /* Handle navigation */ },
                    icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
                    label = { Text("Home") }
                )
                NavigationBarItem (
                    selected = false,
                    onClick = { /* Handle navigation */ },
                    icon = { Icon(Icons.Default.Menu, contentDescription = "Home") },
                    label = { Text("Menu") }
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
                    .height(50.dp),
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
                items(activeUsers) { user ->
                    ActiveUserItem(user)
                }
            }

            // Chat List
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(vertical = 8.dp)
            ) {
                // Filter chats based on search input
                val filteredChats = chats.filter {
                    it.name.contains(input, ignoreCase = true) ||
                            it.lastMessage.contains(input, ignoreCase = true)
                }

                items(filteredChats) { chat ->
                    ChatItem(chat)
                }
            }
        }
    }
}


