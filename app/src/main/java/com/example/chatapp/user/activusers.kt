package com.example.chatapp.user

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@Composable
fun ActiveUserItem(user: Chat, navController: NavHostController) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(horizontal = 8.dp)
            .clickable {
                navController.navigate("message/${user.name}/${user.id}/${user.isonline}/${user.isGroup}")
            }
    ) {
        Box(
            modifier = Modifier
                .size(60.dp)
        ) {
            // User avatar
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(CircleShape)
                    .background(Color.Gray)
            )

            // Online status indicator - only show if user.isOnline is true
            if (user.isonline) {
                Box(
                    modifier = Modifier
                        .size(14.dp)
                        .clip(CircleShape)
                        .background(Color.Green)
                        .align(Alignment.BottomEnd)
                        .padding(2.dp) // Optional: adds a little padding from the edge
                )
            }
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = user.name,
            fontSize = 12.sp,
            color = Color.White
        )
    }
}