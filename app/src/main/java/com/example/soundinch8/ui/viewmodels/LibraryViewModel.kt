package com.example.soundinch8.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.soundinch8.ui.models.Playlist
import com.example.soundinch8.ui.models.PlaylistRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

class LibraryViewModel : ViewModel(){

    private val _selectedTab = MutableStateFlow(0)

    val selectedTab: StateFlow<Int> = _selectedTab.asStateFlow()

    // Points directly to the repository
    val playlists: StateFlow<List<Playlist>> = PlaylistRepository.playlists

    val filteredPlaylist: StateFlow<List<Playlist>> =  combine(
        PlaylistRepository.playlists,
        _selectedTab
    ){
        playlists, tabIndex ->
        when (tabIndex){
            0 -> playlists
            1 -> playlists.filter {it.isFavorite}
            else -> playlists
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    fun toogleFavorite(playlist: Playlist) = PlaylistRepository.toggleFavorite(playlist)
    fun deletePlaylist(playlist: Playlist) = PlaylistRepository.deletePlaylist(playlist)
    fun onTabSelected(index:Int) {_selectedTab.value = index}


}






