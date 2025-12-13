package com.example.chatapp.user


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.material3.MaterialTheme // <-- Use MaterialTheme
import androidx.compose.runtime.collectAsState
import com.example.chatapp.LoginDataStore
import kotlinx.coroutines.launch

data class sitting(
    val name:String,
    val menu:String
)
val SittingList= listOf(
    // Added a small spacing for better readability in the list of settings
    sitting("Account" , menu = "Security notifications , Change number"),
    sitting("Add Friends" , menu = "Block contacts , Disallow messages"),
    sitting("Friend Requests" , menu = "Theme , wallpapers , chat history"),
    sitting("Notifications" , menu = "Message , group ,calls"),
    sitting("Avatars" , menu = "Create,edit ,profile photo"),
    sitting("Storage and data" , menu = "Network usage , auto-download"),
    sitting("Help" , menu = "FAQ , contact us , privacy policy"),
    sitting("Invite a friend" , menu = ""),
    sitting("Log out" , menu = "")
)

@Composable
fun ProfileScreen(navController: NavHostController) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    // Assuming LoginDataStore.getName is correctly implemented to flow the user's name
    val userName = LoginDataStore.getName(context).collectAsState(initial = "User Name").value

    // Use MaterialTheme colors for standard components
    val surfaceColor = MaterialTheme.colorScheme.surface
    val onSurfaceColor = MaterialTheme.colorScheme.onSurface
    val primaryColor = MaterialTheme.colorScheme.primary

    Scaffold(
        containerColor = surfaceColor // Background color for the whole screen
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // --- Top Bar (Header) ---
            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .background(primaryColor) // Use primary color for header
                    .padding(vertical = 8.dp),
                verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
            ){
                IconButton(onClick = {navController.navigate("home") }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "back",
                        tint = onSurfaceColor // Icon color
                    )
                }

                // --- Profile Info Row ---
                Row(
                    modifier = Modifier.padding(start = 8.dp),
                    verticalAlignment = androidx.compose.ui.Alignment.CenterVertically,
                ) {
                    // Profile Circle (Placeholder for an actual image)
                    Box(
                        modifier = Modifier
                            .size(50.dp)
                            .background(Color.Gray, shape = CircleShape)
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(
                        text = userName,
                        color = onSurfaceColor,
                        fontSize = 20.sp,
                        style = MaterialTheme.typography.titleLarge // Use typography
                    )
                }
            }

            // --- Settings List ---
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(SittingList) { list ->
                    SittingOptins(list, onSurfaceColor) { // Pass color to SittingOptins

                        // Handle Clicks
                        when (list.name) {
                            "Log out" -> {
                                scope.launch {
                                    LoginDataStore.clearEmail(context) // Clear DataStore
                                }
                                // Navigate to Login, clearing the back stack
                                navController.navigate("login") {
                                    popUpTo(navController.graph.startDestinationId) { inclusive = true } // Better way to clear stack
                                }
                            }
                            // *** FIX APPLIED HERE: Changed from "Add Friend" (singular) to "Add Friends" (plural) ***
                            "Add Friends" -> {
                                navController.navigate("invite")
                            }
                            "Friend Requests" -> {
                                navController.navigate("request")
                            }
                            //add more

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
    textColor: Color,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 12.dp) // Adjust padding for better spacing
    ) {

        Text(
            text = list.name,
            fontSize = 20.sp,
            color = textColor, // Use passed text color
            style = MaterialTheme.typography.titleMedium
        )

        if (list.menu.isNotEmpty()) {
            Text(
                text = list.menu,
                fontSize = 14.sp,
                color = Color.Gray, // A muted color for the description
                modifier = Modifier.padding(top = 2.dp),
                style = MaterialTheme.typography.bodySmall
            )
        }

        // Separator Line
        Divider(
            color = Color.DarkGray.copy(alpha = 0.5f), // Slightly transparent dark gray
            thickness = 0.5.dp,
            modifier = Modifier.padding(top = 10.dp)
        )
    }
}