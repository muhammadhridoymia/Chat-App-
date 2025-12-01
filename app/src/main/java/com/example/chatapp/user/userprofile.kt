package com.example.chatapp.user


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
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
import com.example.chatapp.LoginDataStore
import kotlinx.coroutines.launch


@Composable
fun ProfileScreen(navController: NavHostController) {
    val context= LocalContext.current
    val scop= rememberCoroutineScope()

    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
                .padding(paddingValues)
        ) {
            Text("Profile",
                fontSize = 30.sp,
                color = Color.White,
                modifier = Modifier.padding(start = 10.dp)
                )
            Divider(
                color = Color.White,
                thickness = 1.dp,
            )
            Spacer(modifier = Modifier.height(20.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(60.dp)
                        .background(Color.White, shape = CircleShape)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 16.dp)
                ) {
                    Text(
                        text = "Hridoy",
                        fontSize = 20.sp,
                        color = Color.White
                    )
                    Text(
                        "hridoy@gmail.com",
                        fontSize = 10.sp,
                        color = Color.White
                    )
                    Text("+88 01850511158",
                        fontSize = 10.sp,
                        color = Color.White
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                "⚙\uFE0F Setting",
                fontSize = 30.sp,
                color = Color.White,
                modifier = Modifier.padding(start = 10.dp)
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text("◉ Add Number",
                fontSize = 20.sp,
                color = Color.Green,
                modifier = Modifier.padding(start = 10.dp)
            )
            Spacer(modifier = Modifier.height(20.dp))

            Text(
                " ◉ Request",
                fontSize = 20.sp,
                color = Color.White,
                modifier = Modifier.padding(start = 10.dp)
            )
            Spacer(modifier = Modifier.height(20.dp))

            Text(
                "◉ Create Group",
                fontSize = 20.sp,
                color = Color.White,
                modifier = Modifier.padding(start = 10.dp)
            )
            Spacer(modifier = Modifier.height(20.dp))

            Text(
                "◉ Join Group",
                fontSize = 20.sp,
                color = Color.White,
                modifier = Modifier.padding(start = 10.dp)
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text("◉ Change Profile",
                fontSize = 20.sp,
                color = Color.White,
                modifier = Modifier.padding(start = 10.dp)
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text("◉ Change Password",
                fontSize = 20.sp,
                color = Color.White,
                modifier = Modifier.padding(start = 10.dp)
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text("◉ Help Services",
                fontSize = 20.sp,
                color = Color.White,
                modifier = Modifier.padding(start = 10.dp)
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text("◉ Notification",
                fontSize = 20.sp,
                color = Color.White,
                modifier = Modifier.padding(start = 10.dp)
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text("◉ Terms & Conditions",
                fontSize = 20.sp,
                color = Color.White,
                modifier = Modifier.padding(start = 10.dp)
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text("◉ Privacy Policy",
                fontSize = 20.sp,
                color = Color.White,
                modifier = Modifier.padding(start = 10.dp)
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text("◉ Delete Account",
                fontSize = 20.sp,
                color = Color.Red,
                modifier = Modifier.padding(start = 10.dp)
            )

            Button(
                onClick = { scop.launch { LoginDataStore.clearEmail(context) }},
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color.Red)
                    .padding(horizontal = 30.dp, vertical = 20.dp)
            ) {
                Text("Log Out")
            }
        }
    }
}
