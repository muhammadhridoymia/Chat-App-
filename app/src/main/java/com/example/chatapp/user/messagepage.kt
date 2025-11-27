package com.example.chatapp.user

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Navigation
import androidx.compose.material.icons.filled.VideoCall
import androidx.compose.material.icons.filled.Videocam
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun MessagePage(name: String) {
    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Row(
                modifier = Modifier.padding(0.dp)
                    .background(Color.Blue)
            ) {
                IconButton(onClick = {}) {
                    Icon(Icons.Default.ArrowBackIosNew, contentDescription = "Menu")
                }
                Box(
                    modifier = Modifier
                        .size(60.dp)
                        .background(Color.Gray, shape = RoundedCornerShape(30.dp))

                )
                Column(
                    modifier = Modifier.padding(start = 16.dp)

                ) {
                    Text(name , color = Color.White, fontSize = 20.sp)
                    Text("online", color = Color.Green, fontSize = 10.sp)
                }
                IconButton(onClick = { "" }) {
                    Icon(Icons.Default.VideoCall, contentDescription = "Menu")
                }
                IconButton(onClick = { "" }) {
                    Icon(Icons.Default.Call, contentDescription = "Menu")
                }
                IconButton(onClick = { "" }) {
                    Icon(Icons.Default.Menu, contentDescription = "Menu")
                }
            }
        }
    }
}
