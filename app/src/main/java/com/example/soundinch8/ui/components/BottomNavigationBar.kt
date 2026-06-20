package com.example.soundinch8.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LibraryMusic
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.soundinch8.ui.navigation.SoundInRoutes

@Composable
fun BottomNavigationBar(
    currentRoute: String?,
    onNavigate: (String) -> Unit
) {
    NavigationBar {
        val items = listOf(
            Triple(SoundInRoutes.LIBRARY, Icons.Default.LibraryMusic, "Library"),
            Triple(SoundInRoutes.SEARCH, Icons.Default.Search, "Search"),
            Triple(SoundInRoutes.PROFILE, Icons.Default.Person, "Profile"),
        )
        items.forEach { (route, icon, label) ->
            NavigationBarItem(
                selected = currentRoute == route,
                onClick = { onNavigate(route) },
                icon = { Icon(icon, contentDescription = label) },
                label = { Text(label) }
            )
        }
    }
}







