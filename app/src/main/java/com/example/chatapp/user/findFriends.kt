package com.example.chatapp.user

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import com.example.chatapp.network.RetrofitClient
import kotlinx.coroutines.launch
import com.example.chatapp.network.SingleUserResponse


@Composable
fun FindFriends(navController: NavHostController) {

    var email by remember { mutableStateOf("") }
    var user by remember { mutableStateOf<SingleUserResponse?>(null) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    val coroutine = rememberCoroutineScope()

     fun searchUser() {
        coroutine.launch {
            try {
                val response = RetrofitClient.findfrined.findUserByEmail(email)
                user = response
                errorMessage = null
            } catch (e: Exception) {
                user = null
                errorMessage = "User not found"
                return@launch
            }

        }
    }

    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {

            Text("Find Friends", modifier = Modifier.padding(10.dp), fontSize = 30.sp)

            Row(modifier = Modifier.fillMaxWidth()) {

                OutlinedTextField(
                    modifier = Modifier.padding(10.dp),
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Search") }
                )

                IconButton(
                    onClick = { searchUser() },
                    modifier = Modifier
                        .padding(15.dp)
                        .background(Color.Blue, shape = androidx.compose.foundation.shape.CircleShape)
                ) {
                    Icon(Icons.Default.Search, contentDescription = "Search")
                }
            }

            // Show error message
            errorMessage?.let {
                Text(
                    text = it,
                    color = Color.Red,
                    modifier = Modifier.padding(10.dp)
                )
            }

            // Show user (IF FOUND)
            user?.let { found ->
                Row(
                    modifier = Modifier
                        .padding(10.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = androidx.compose.foundation.layout.Arrangement.SpaceBetween,
                    verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
                ) {

                    Box(
                        modifier = Modifier
                            .size(50.dp)
                            .background(Color.Gray, shape = androidx.compose.foundation.shape.CircleShape)
                    )

                    Text(found.user.name)

                    Button(onClick = { /* Add Friend */ }) {
                        Text("Add")
                    }
                }
            }
        }
    }
}