package com.example.chatapp

import androidx.compose.runtime.Composable
import androidx.navigation.compose.composable

//Screens
import com.example.chatapp.user.HomeScreen
import com.example.chatapp.user.ProfileScreen
import com.example.chatapp.user.NotificationsScreen
import  com.example.chatapp.user.MessagePage


import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument


@Composable
fun Navigation(){
    val navController: NavHostController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "home"
    ){
        composable("home"){
            HomeScreen(navController = navController)
        }
        composable("profile"){
            ProfileScreen(navController=navController)
        }
        composable("notification"){
            NotificationsScreen()
        }
        composable(
            route = "message/{name}",
            arguments = listOf(navArgument("name") { type = NavType.StringType })
        ){ backStackEntry ->
            val name = backStackEntry.arguments?.getString("name")
            MessagePage( name = name ?: "")
        }
    }
}
