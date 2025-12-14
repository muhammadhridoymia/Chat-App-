package com.example.chatapp.user

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.chatapp.LoginDataStore
import com.example.chatapp.network.FriendRequestResponse
import com.example.chatapp.network.FriendResponse
import com.example.chatapp.network.RetrofitClient


@Composable
fun AllFriendRequestsScreen(navController: NavHostController){

    val context = LocalContext.current
    val userId by LoginDataStore.getId(context).collectAsState(initial = "")

    var requests by remember { mutableStateOf<List< FriendRequestResponse>>(emptyList()) }


    LaunchedEffect(userId) {
        try {
            if (userId.isNotEmpty()) {
                requests = RetrofitClient.fetchFriendReqs.getFriendReq(userId)

            } else {
                Toast.makeText(context, "User not found", Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception) {
            Toast.makeText(
                context,
                "Error fetching chats: ${e.message}",
                Toast.LENGTH_SHORT
            ).show()
        }
    }




    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Text("All Friend Requests", modifier = Modifier.padding(10.dp), fontSize = 30.sp)
            if (requests.isEmpty()){
                Text("No Requests")
            }else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(requests.size) { request ->
                        if (requests.isEmpty()) {
                            Text("No Requests")
                        } else {
                            Row(
                                modifier = Modifier
                                    .background(color = Color.Gray)
                                    .fillMaxWidth()
                                    .padding(horizontal = 5.dp, vertical = 8.dp),
                                horizontalArrangement = androidx.compose.foundation.layout.Arrangement.SpaceBetween,
                                verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier.padding(10.dp)
                                        .size(50.dp)
                                        .background(
                                            color = Color.White,
                                            shape = RoundedCornerShape(25.dp)
                                        )

                                )
                                Text(requests[request].fromUser.name, fontSize = 20.sp)
                                Button(onClick = {}) {
                                    Text("Accept")
                                }
                                Button(onClick = {}) {
                                    Text("Reject")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

