package com.example.chatapp

import androidx.compose.runtime.Composable
import androidx.navigation.compose.composable
import com.example.chatapp.user.HomeScreen
import com.example.chatapp.user.ProfileScreen
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHost
import androidx.navigation.compose.rememberNavController


@Composable
fun navigation (){
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
    }
}

