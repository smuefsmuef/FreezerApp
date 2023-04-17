package fhnw.emoba.freezerapp.model

import Album
import AlbumSong
import Radio
import Song
import android.media.AudioAttributes
import android.media.MediaPlayer
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import fhnw.emoba.freezerapp.DeezerApi
import fhnw.emoba.freezerapp.DeezerApi.Companion.loadImage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch


object FreezerModel {
    var title = "Hoi Deezer"
    private val backgroundJob = SupervisorJob()
    private val modelScope = CoroutineScope(backgroundJob)

    var currentScreen by mutableStateOf(Screen.HITS)
    var darkTheme by mutableStateOf(false)

    var isLoading by mutableStateOf(false)
    var isPlayerReady by mutableStateOf(true)
    var isListView by mutableStateOf(false)

    var filter by mutableStateOf("")
    var currentSongList: List<Song> by mutableStateOf(emptyList())
    var currentAlbumList: List<Album> by mutableStateOf(emptyList())
    var currentRadioList: List<Radio> by mutableStateOf(emptyList())
    var favoriteList: List<Song> by mutableStateOf(emptyList())
    var currentAlbum: Album? by mutableStateOf(null)
    var currentAlbumSonglist: List<AlbumSong> by mutableStateOf(emptyList())
    var currentRadioSonglist: List<Song> by mutableStateOf(emptyList())

    private val player = MediaPlayer().apply {
        setOnCompletionListener { isPlayerReady = true }
        setAudioAttributes(
            AudioAttributes.Builder().setContentType(AudioAttributes.CONTENT_TYPE_MUSIC).build()
        )
        setOnPreparedListener {
            start()
        }
    }

    fun toggleTheme() {
        darkTheme = !darkTheme
    }

    fun startPlayer(song: Song) {
        song.isPlaying = true
        isPlayerReady = false
        try {
            player.reset()
            player.setDataSource(song.preview)
            player.prepareAsync()
        } catch (e: Exception) {
            isPlayerReady = true
        }
    }

    fun pausePlayer(song: Song) {
        song.isPlaying = false
        player.pause()
        isPlayerReady = true
    }

    fun fromStart() {
        player.seekTo(0)
        player.start()
        isPlayerReady = false
    }

    fun playNextSong(song: Song) {
        pausePlayer(song)
        when (currentScreen) {
            Screen.SONGS -> {
                if (currentSongList.isNotEmpty()) {
                    if (song == currentSongList.last()) {
                        fromStart()
                        song.isPlaying = true
                    } else {
                        val test = currentSongList.indexOf(song)
                        startPlayer(currentSongList[test + 1])
                    }
                }
            }
            Screen.RADIO -> {
                if (currentRadioSonglist.isNotEmpty()) {
                    if (song == currentRadioSonglist.last()) {
                        fromStart()
                        song.isPlaying = true
                    } else {
                        val test = currentRadioSonglist.indexOf(song)
                        startPlayer(currentRadioSonglist[test + 1])
                    }
                }
            }
            Screen.HITS -> {
                if (favoriteList.isNotEmpty()) {
                    if (song == favoriteList.last()) {
                        fromStart()
                        song.isPlaying = true
                    } else {
                        val test = favoriteList.indexOf(song)
                        startPlayer(favoriteList[test + 1])
                    }
                }
            }
            else -> {
                // nothing
            }
        }
    }

    fun launchSearch() {
        isLoading = true
        currentSongList = emptyList()
        currentAlbumList = emptyList()
        currentRadioList = emptyList()

        modelScope.launch {
            if (filter != "") {
                when (currentScreen) {
                    Screen.SONGS -> {
                        currentSongList = DeezerApi.searchTrack(filter)
                    }
                    Screen.ALBUMS -> {
                        currentAlbumList = DeezerApi.searchAlbum(filter)
                    }
                    Screen.RADIO -> {
                        currentRadioList = DeezerApi.searchRadio(filter)
                    }
                    else -> {}
                }
                for (song in currentSongList) {
                    song.imageBitmap = loadImage(song.artist.picture_small!!)
                }
                for (album in currentAlbumList) {
                    album.imageBitmapAlbum = loadImage(album.cover_small)
                }
                for (radio in currentRadioList) {
                    radio.imageBitmapRadio = loadImage(radio.picture_small)
                }
            }
        }
        isLoading = false
    }

    fun handleFavoriteList(song: Song) {
        song.isFavorite = !song.isFavorite
        if (song.isFavorite) {
            favoriteList = favoriteList.plus(song)
        } else {
            favoriteList = favoriteList.minus(song)
        }
    }

    fun convertSecondsToMinutes(song: Song): String {
        val seconds = song.duration
        val minutes = seconds / 60
        val remainingSeconds = seconds % 60
        return "$minutes m $remainingSeconds s"
    }

    fun songListSearch(album: Album) {
        isListView = true
        currentAlbum = album
        isLoading = true
        modelScope.launch {
            currentAlbumSonglist = DeezerApi.searchAlbumTrackList(currentAlbum!!)
        }
        isLoading = false
    }

    fun radioSongListSearch(radio: Radio) {
        isListView = true
        isLoading = true
        modelScope.launch {
            currentRadioSonglist = DeezerApi.searchRadioTrackList(radio)
            for (song in currentRadioSonglist) {
                song.imageBitmap = loadImage(song.artist.picture_small!!)
            }
        }
        isLoading = false
    }


}
