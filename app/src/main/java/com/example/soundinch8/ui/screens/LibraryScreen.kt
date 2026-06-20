package com.example.soundinch8.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.soundinch8.ui.components.PlaylistBottomSheet
import com.example.soundinch8.ui.components.PlaylistCard
import com.example.soundinch8.ui.models.Playlist
import com.example.soundinch8.ui.theme.SoundInCh8Theme
import com.example.soundinch8.ui.viewmodels.LibraryViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LibraryScreen(
    viewModel: LibraryViewModel = viewModel(),
    onNavigateToPlaylistDetail: (Playlist) -> Unit
) {
    val selectedTab by viewModel.selectedTab.collectAsStateWithLifecycle()
    val filteredPlaylist by viewModel.filteredPlaylist.collectAsStateWithLifecycle()
    var selectedPlaylist by remember { mutableStateOf<Playlist?>(null) }

    Scaffold(
        topBar = {
            Column {
                TopAppBar(
                    title = {
                        Text(
                            text = "Library",
                            fontWeight = FontWeight.Bold
                        )
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        titleContentColor = MaterialTheme.colorScheme.onPrimary
                    )
                ) // end of TopAppBar
                TabRow(
                    selectedTabIndex = selectedTab,
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                ) {
                    val tabs = listOf("All", "Favorites")
                    tabs.forEachIndexed { index, title ->
                        Tab(
                            selected = selectedTab == index,
                            onClick = { viewModel.onTabSelected(index) },
                            text = { Text(text = title) },
                            selectedContentColor = MaterialTheme.colorScheme.onPrimary,
                            unselectedContentColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.5f)
                        )
                    }
                }
            }
        }
    ) { paddingValues ->
        if (filteredPlaylist.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = if (selectedTab == 1)
                        "No favorite Playlist Yet"
                    else "No playlist found",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
            }
        } else {
            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = 160.dp),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)

            ) {
                items(filteredPlaylist, key = {it.id}) {playlist ->
                    PlaylistCard(
                        playlist = playlist,
                        onClick = {onNavigateToPlaylistDetail(playlist)},
                        onLongClick = {selectedPlaylist = playlist}
                    )

                }
            }
        }
    } // end of Scaffold
    selectedPlaylist?.let {playlist ->
        PlaylistBottomSheet(
            playlist = playlist,
            onDismiss = {selectedPlaylist = null},
            onToggleFavorite = {viewModel.toogleFavorite(it)},
            onDelete = {viewModel.deletePlaylist(it)}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun LibraryScreenPreview() {
    SoundInCh8Theme {
        LibraryScreen(
            onNavigateToPlaylistDetail = {}
        )
    }
}





