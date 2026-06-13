package com.fixnow.soundinch8.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.fixnow.soundinch8.ui.screens.LoginScreen
import com.fixnow.soundinch8.ui.screens.MainScreen
import com.fixnow.soundinch8.ui.screens.RegisterScreen

@Composable
fun SoundInNavGraph(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = SoundInRoutes.LOGIN
    ){
        composable(SoundInRoutes.LOGIN){
            LoginScreen(
                onNavigateToRegister = {},
                onLoginSuccess = {
                    navController.navigate(SoundInRoutes.MAIN){
                        popUpTo(SoundInRoutes.LOGIN){ inclusive = true }
                    }
                }
            )
        } // end composable LOGIN
        composable (SoundInRoutes.MAIN){
            MainScreen()
        }
    }
}
