package com.example.bartersystem

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.bartersystem.screens.*

object Routes {
    const val LOGIN      = "login"
    const val REGISTER   = "register"
    const val HOME       = "home"
    const val PROFILE    = "profile"
    const val NEED_POST  = "need_post"
    const val SWAP_OFFER = "swap_offer"
    const val CHAT       = "chat"
}

@Composable
fun BarterNavHost(navController: NavHostController) {
    NavHost(
        navController    = navController,
        startDestination = Routes.LOGIN
    ) {
        composable(Routes.LOGIN)      { LoginScreen(navController) }
        composable(Routes.REGISTER)   { RegisterScreen(navController) }
        composable(Routes.HOME)       { HomeScreen(navController) }
        composable(Routes.PROFILE)    { ProfileScreen(navController) }
        composable(Routes.NEED_POST)  { NeedPostScreen(navController) }
        composable(Routes.SWAP_OFFER) { SwapOfferScreen(navController) }
        composable(Routes.CHAT)       { ChatScreen(navController) }
    }
}