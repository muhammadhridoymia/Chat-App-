package com.example.chatapp.user


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import  androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import com.example.chatapp.LoginDataStore
import kotlinx.coroutines.launch

data class sitting(
    val name:String,
    val menu:String
)
val SittingList= listOf(
     sitting("Account" , menu = "Security notifications , Change number")
    ,sitting("Privacy" , menu = "Block contacts , Disallow messages")
    ,sitting("Chats" , menu = "Theme , wallpapers , chat history")
    ,sitting("Notifications" , menu = "Message , group ,calls")
    ,sitting("Avatars" , menu = "Create,edit ,profile photo")
    ,sitting("Storage and data" , menu = "Network usage , auto-download")
    ,sitting("Help" , menu = "FAQ , contact us , privacy policy")
    ,sitting("Invite a friend" , menu = "")
    ,sitting("Log out" , menu = "")

)
@Composable
fun ProfileScreen(navController: NavHostController) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
                .padding(paddingValues)
        ) {
            Row (
                modifier = Modifier.padding(0.dp)
                    .fillMaxWidth()
                    .background(Color.Blue),

            ){
                IconButton(onClick = {navController.navigate("home") }) {
                    Icon(Icons.Default.ArrowBack , contentDescription = "back")
                }
                Column(
                    modifier = Modifier.padding(start = 16.dp)

                ) {
                    Row(
                        modifier = Modifier,
                        verticalAlignment = androidx.compose.ui.Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween


                    ) {
                        Box(
                            modifier = Modifier
                                .padding(horizontal = 5.dp)
                                .size(50.dp)
                                .background(Color.Gray, shape = CircleShape)
                        )
                        Text("Hridoy" , color = Color.White , fontSize = 20.sp)
                    }
                }
            }
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(SittingList) { list ->
                    SittingOptins(list) {

                        if (list.name == "Log out") {
                            // Logout here
                            scope.launch {
                                LoginDataStore.clearEmail(context)   // <-- Clear DataStore
                            }
                            navController.navigate("login") {   // <-- Go to Login
                                popUpTo(0) { inclusive = true }
                            }
                        }
                    }
                }
            }

        }
    }
}


@Composable
fun SittingOptins(
    list: sitting,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable { onClick() }     // <-- User clicks this row
    ) {

        Text(
            text = list.name,
            fontSize = 20.sp,
            color = Color.White
        )

        if (list.menu.isNotEmpty()) {
            Text(
                text = list.menu,
                fontSize = 14.sp,
                color = Color.Gray,
                modifier = Modifier.padding(top = 4.dp)
            )
        }

        Divider(
            color = Color.DarkGray,
            thickness = 1.dp,
            modifier = Modifier.padding(top = 12.dp)
        )
    }
}


