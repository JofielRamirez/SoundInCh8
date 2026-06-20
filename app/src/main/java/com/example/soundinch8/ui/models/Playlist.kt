package com.example.soundinch8.ui.models

data class Playlist(
    val id:Int,
    val name: String,
    val genre:String,
    val songCount: Int,
    val colorHex: String,
    val isFavorite: Boolean = false

)
