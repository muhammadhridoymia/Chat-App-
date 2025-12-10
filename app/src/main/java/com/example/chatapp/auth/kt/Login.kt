package com.example.chatapp.auth

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.chatapp.LoginDataStore
import com.example.chatapp.network.LoginRequest
import kotlinx.coroutines.launch
import com.example.chatapp.network.RetrofitClient

@Composable
fun LoginScreen(navController: NavHostController, onLoginSuccess: () -> Unit ) {

    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        Modifier
            .fillMaxSize()
            .background(color=Color.White)
            .padding(20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text("Log In", style = MaterialTheme.typography.headlineMedium)

        Spacer(Modifier.height(20.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") }
        )

        Spacer(Modifier.height(10.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") }
        )

        Spacer(Modifier.height(20.dp))

        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(context, "Enter email and password", Toast.LENGTH_SHORT).show()
                } else {
                    scope.launch {
                        try {
                            // 1. Call backend
                            val response = RetrofitClient.api.login(
                                LoginRequest(email, password)
                            )

                            // 2. Check response
                            if (response.user!= null) {
                                Toast.makeText(context, response.message, Toast.LENGTH_SHORT).show()

                                // 3. Save email or token
                                LoginDataStore.userSave(context,response.user!!.name,response.user!!.email,response.user!!.id)

                                // 4. Navigate to home screen
                                onLoginSuccess()

                            } else {
                                Toast.makeText(context, response.message, Toast.LENGTH_SHORT).show()
                            }

                        } catch (e: Exception) {
                            Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }
        ) {
            Text("Login")
        }

        Button(onClick = { navController.navigate("signin") }) {
            Text("Create Account")
        }
    }
}
