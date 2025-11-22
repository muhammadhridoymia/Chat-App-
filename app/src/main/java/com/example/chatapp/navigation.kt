package com.example.chatapp

import androidx.compose.runtime.Composable
import androidx.navigation.compose.composable
import com.example.chatapp.user.HomeScreen
import com.example.chatapp.user.userprofile
import com.example.chatapp.user.HomeScreen
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHost
import androidx.navigation.compose.rememberNavController


@Composable
fun navigation (){
    NavHost(
        navController = rememberNavController(),
        startDestination = "home"
    ){
        composable("home"){
            HomeScreen(navController = rememberNavController())
        }
        composable("profile"){
            userprofile()
        }
    }
}

