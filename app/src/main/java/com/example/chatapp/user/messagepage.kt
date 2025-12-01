package com.example.chatapp.user

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Photo
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.VideoCall
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
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
            //Top Header
            Row(
                modifier = Modifier.padding(0.dp)
                    .fillMaxWidth()
                    .background(Color.Blue),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
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
            //Message
            Column (
                modifier = Modifier.weight(1f)
            ){  }

            //Bottom bar
            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp)
                    .background(Color.Black),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                IconButton(onClick = {}) {
                    Icon(Icons.Default.Photo, contentDescription = "Menu")
                }
                IconButton(onClick = {}) {
                    Icon(Icons.Default.CameraAlt, contentDescription = "Menu")
                }
                IconButton(onClick = {}) {
                    Icon(Icons.Default.Mic, contentDescription = "Mic")
                }
                TextField(
                    value = "",
                    onValueChange = {},
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 15.dp, vertical = 0.dp),
                    shape = RoundedCornerShape(10.dp),
                )
                IconButton(onClick = {}) {
                    Icon(Icons.Default.Send, contentDescription = "Video")
                }
            }
        }
    }
}
