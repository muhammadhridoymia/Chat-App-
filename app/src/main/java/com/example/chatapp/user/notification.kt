package com.example.chatapp.user

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class NotificationItem(
    val name: String,
    val lastMessage: String
)

val notifications = listOf(
    NotificationItem("Hridoy", "You have a new message from Alice."),
    NotificationItem("Rakib", "Bob has sent you a friend request."),
    NotificationItem("Riaz khan", "The group has been updated."),
    NotificationItem("Raju", "You have a new message from Alice."),
    NotificationItem("Manik", "Bob has sent you a friend request."),
    NotificationItem("Shadhin", "The group has been updated."),
)

@Composable
fun NotificationsScreen() {
    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
                .padding(paddingValues)
        ) {
            // Header
            Text(
                "Notifications",
                fontSize = 30.sp,
                color = Color.White,
                modifier = Modifier.padding(16.dp)
            )

            Divider(
                color = Color.White,
                thickness = 1.dp
            )

            // List
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(notifications) { notification ->
                    Column(
                        modifier = Modifier
                            .padding(16.dp)
                    ) {
                        Row (
                            modifier = Modifier.fillMaxWidth()

                        ){
                            Box(
                                modifier = Modifier
                                    .size(60.dp)
                                    .background(Color.Gray , shape = RoundedCornerShape(25.dp))
                            )
                            Spacer(modifier = Modifier.width(16.dp))
                            Column(
                                modifier = Modifier.weight(1f)
                            ) {
                                Text(
                                    text = notification.name,
                                    fontSize = 20.sp,
                                    color = Color.White
                                )
                                Text(
                                    text = notification.lastMessage,
                                    fontSize = 15.sp,
                                    color = Color.Gray
                                )

                                Divider(
                                    color = Color.DarkGray,
                                    thickness = 1.dp,
                                    modifier = Modifier.padding(top = 8.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
