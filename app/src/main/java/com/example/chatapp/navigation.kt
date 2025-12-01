package com.example.chatapp

import androidx.compose.runtime.Composable
import androidx.navigation.compose.composable

//Screens
import com.example.chatapp.dataStore
import com.example.chatapp.user.HomeScreen
import com.example.chatapp.user.ProfileScreen
import com.example.chatapp.user.NotificationsScreen
import  com.example.chatapp.user.MessagePage
import  com.example.chatapp.auth.LoginScreen
import com.example.chatapp.auth.SigninScreen



import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument


@Composable
fun Navigation() {

    val context = LocalContext.current

    val emailFlow = LoginDataStore.getEmail(context)
    val email by emailFlow.collectAsState(initial = "")

    val startDestination = if (email.isNotEmpty()) "home" else "login"

    val navController: NavHostController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {

        composable("home") {
            HomeScreen(navController = navController)
        }

        composable("profile") {
            ProfileScreen(navController = navController)
        }

        composable("notification") {
            NotificationsScreen()
        }

        composable(
            route = "message/{name}",
            arguments = listOf(navArgument("name") { type = NavType.StringType })
        ) { backStackEntry ->
            val name = backStackEntry.arguments?.getString("name")
            MessagePage(name = name ?: "")
        }

        composable("login") {
            LoginScreen(navController=navController,
                onLoginSuccess = {
                    navController.navigate("home") {
                        popUpTo("login") {
                            inclusive = true
                        }
                    }
                }
            )
        }
        composable("signin") {
            SigninScreen(navController=navController)
        }
    }
}
