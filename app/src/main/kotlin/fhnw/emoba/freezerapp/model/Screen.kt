package fhnw.emoba.freezerapp.model

import androidx.annotation.DrawableRes
import fhnw.emoba.R

enum class Screen(
    val title: String,
    @DrawableRes val resId: Int,
    val color: androidx.compose.ui.graphics.Color,
    val mainTab: Boolean
) {
    HITS("My Hits", R.drawable.background, androidx.compose.ui.graphics.Color.Green, true),
    SONGS("Songs", R.drawable.deezerlogo, androidx.compose.ui.graphics.Color.Blue, true),
    ALBUMS("Albums", R.drawable.deezerlogo, androidx.compose.ui.graphics.Color.Red, true),
    RADIO("Radios", R.drawable.deezerlogo, androidx.compose.ui.graphics.Color.Yellow, true),
}