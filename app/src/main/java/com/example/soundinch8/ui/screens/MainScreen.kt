package com.example.soundinch8.ui.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.soundinch8.ui.components.BottomNavigationBar
import com.example.soundinch8.ui.models.Playlist
import com.example.soundinch8.ui.navigation.SoundInRoutes
import com.example.soundinch8.ui.viewmodels.UserSessionViewModel

@Composable
fun MainScreen(
    sessionViewModel: UserSessionViewModel,
    onLogout: () -> Unit,
    onNavigateToPlaylistDetail: (Playlist) -> Unit
) {
    val navController = rememberNavController()
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                currentRoute = currentRoute,
                onNavigate = { route ->
                    navController.navigate(route){
                        popUpTo (navController.graph.startDestinationId){
                            saveState = true
                        } // end popUpTo
                        launchSingleTop = true
                        restoreState = true
                    }// end  navController.navigate
                } // end onNavigate
            )
        }
    ) {
        paddingValues ->
        NavHost(
            navController = navController,
            startDestination = SoundInRoutes.LIBRARY,
            modifier = Modifier.padding(paddingValues)
        ){
            composable (SoundInRoutes.LIBRARY){ LibraryScreen(
                onNavigateToPlaylistDetail = onNavigateToPlaylistDetail
            ) }
            composable (SoundInRoutes.SEARCH){ SearchScreen() }
            composable (SoundInRoutes.PROFILE){
                ProfileScreen(
                sessionViewModel = sessionViewModel,
                    onLogout = onLogout
            ) }
        }
    }

}










