package fhnw.emoba.freezerapp

import Album
import AlbumSong
import Radio
import Song
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Test
import org.junit.runner.RunWith
import org.json.JSONObject
import org.junit.Assert.*
import java.io.InputStream
import java.net.URL
import javax.net.ssl.HttpsURLConnection

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 *
 */
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
              "cover_small": "https://test.url.jpg"
                      
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

    val testTrackList = listOf(
        AlbumSong(JSONObject("""{"id":"1","title":"Track 1", "preview":"https://test.ch", "artist":{"name": "Mc A"}}""")),
        AlbumSong(JSONObject("""{"id":"2","title":"Track 2", "preview":"https://test2.ch", "artist":{"name": "Mc B"}}""")),
        AlbumSong(JSONObject("""{"id":"3","title":"Track 3", "preview":"https://test3.ch", "artist":{"name": "Mc C"}}"""))
    )

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