package fhnw.emoba.freezerapp

import Album
import Radio
import Song
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Test
import org.junit.runner.RunWith
import org.json.JSONObject
import org.junit.Assert.*

@RunWith(AndroidJUnit4::class)
internal class DeezerServiceTest {

    private val service = DeezerApi

    private val testSong = """
            {
              "id": "1234",
              "title": "It's a bloody sunday",
              "duration": "325",
              "preview": "https://test.url",
              "artist": {
                "name": "Bon Harley",
                "picture_small": "https://test.url.jpg"
              }
            }
    """.trimIndent()

    private val testAlbum = """
             {
              "id": "23523",
              "title": "It's a bloody sunday",
              "tracklist": "https://test.url",        
              "cover_small": "https://test.url.jpg",
                "artist": {
              "name": "Pink Ployd"
              }             
            }
    """.trimIndent()

    private val testRadio = """
             {
              "id": "23523",
              "title": "It's a bloody sunday",
              "tracklist": "https://test.url",                  
                "picture_small": "https://test.url.jpg"
            }
    """.trimIndent()


    @Test
    fun searchTrackTest() {
        //given
        val songAsJSON = JSONObject(testSong)

        //when
        val testSong = Song(songAsJSON)

        //when
        val song = service.searchTrack("long")

        //then
        assertNotSame(testSong, song)
        assertTrue(song.isNotEmpty())
    }

    @Test
    fun searchRadioTest() {
        //given
        val radioAsJSON = JSONObject(testRadio)

        //when
        val test = Radio(radioAsJSON)

        //when
        val radio = service.searchRadio("P")

        //then
        assertNotSame(test, radio)
        assertTrue(radio.isNotEmpty())

    }

    @Test
    fun searchAlbumTest() {
        //given
        val albumAsJSON = JSONObject(testAlbum)
        val testAlbum = Album(albumAsJSON)

        //when
        val album = service.searchAlbum("Best")

        //then
        assertNotSame(testAlbum, album)
        assertTrue(album.isNotEmpty())
    }


}