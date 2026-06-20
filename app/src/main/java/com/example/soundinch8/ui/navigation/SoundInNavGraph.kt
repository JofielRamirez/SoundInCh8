package com.example.soundinch8.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.soundinch8.ui.screens.LoginScreen
import com.example.soundinch8.ui.screens.MainScreen
import com.example.soundinch8.ui.screens.PlaylistDetailScreen
import com.example.soundinch8.ui.viewmodels.UserSessionViewModel

@Composable
fun SoundInNavGraph(
    navController: NavHostController,
    sessionViewModel: UserSessionViewModel
) {
    NavHost(
        navController = navController,
        startDestination = SoundInRoutes.LOGIN
    ) {
        composable(SoundInRoutes.LOGIN) {
            LoginScreen(
                sessionViewModel = sessionViewModel,
                onNavigateToRegister = {},
                onLoginSuccess = {
                    navController.navigate(SoundInRoutes.MAIN) {
                        popUpTo(SoundInRoutes.LOGIN) { inclusive = true }
                    }
                }
            )
        }// end composable LOGIN
        composable (SoundInRoutes.MAIN){
            MainScreen(
                sessionViewModel = sessionViewModel,
                onLogout = {
                    navController.navigate(SoundInRoutes.LOGIN){
                        popUpTo(SoundInRoutes.MAIN) {inclusive = true}
                    }
                },
                onNavigateToPlaylistDetail = {playlist ->
                    navController.navigate("playlistDetail/${playlist.id}")
                }
            )
        }
        composable (
            route = SoundInRoutes.PLAYLIST_DETAIL,
            arguments = listOf(navArgument("playlistId"){type = NavType.IntType})
        ){
            backStackEntry ->
            val playlistId = backStackEntry.arguments?.getInt("playlistId") ?: 0
            PlaylistDetailScreen (
                onNavigateBack = {navController.popBackStack()}
            )
        }
    }
}














