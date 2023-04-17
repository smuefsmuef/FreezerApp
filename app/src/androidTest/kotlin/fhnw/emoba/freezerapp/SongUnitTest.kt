package fhnw.emoba.freezerapp

import Song
import android.graphics.Bitmap
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.asImageBitmap
import org.json.JSONObject
import org.junit.Test

import org.junit.Assert.*


class SongUnitTest {

    private val singleSongAsString = """
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

    var imageBitmapTest by mutableStateOf(
        Bitmap.createBitmap(30, 30, Bitmap.Config.ALPHA_8).asImageBitmap()
    )

    @Test
    fun testConstructor() {
        //given
        val songAsJSON = JSONObject(singleSongAsString)

        //when
        val song = Song(songAsJSON)

        //then
        with(song) {
            assertEquals(1234, id)
            assertEquals("It's a bloody sunday", title)
            assertEquals(325, duration)
            assertEquals("https://test.url", preview)
            assertEquals("Bon Harley", artist.name)
            assertEquals("https://test.url.jpg", artist.picture_small)
            assertEquals(30, imageBitmapTest.width)
            assertEquals(30, imageBitmapTest.height)
        }
    }

}