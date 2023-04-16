package fhnw.emoba.freezerapp

import Song
import fhnw.emoba.freezerapp.model.FreezerModel
import org.json.JSONObject
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 *
 *  ACHTUNG: Auf Android arbeiten wir (noch) mit JUNIT 4
 */

class FreezerModelTest {

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
    val songAsJSON = JSONObject(singleSongAsString)

    //when
    val song = Song(songAsJSON)


    @Test
    fun startPlayerTest() {

        // given
        val song = Song(songAsJSON)

        //when
        FreezerModel.startPlayer(song)

        //then
        assertTrue(song.isPlaying)
        assertFalse(FreezerModel.playerIsReady)
    }


    @Test
    fun pausePlayerTest() {
        // given
        val song = Song(songAsJSON)

        //when
        FreezerModel.pausePlayer(song)

        //then
        assertFalse(song.isPlaying)
        assertTrue(FreezerModel.playerIsReady)
    }

    @Test
    fun fromStartTest() {
        //when
        FreezerModel.fromStart()

        //then
        assertFalse(FreezerModel.playerIsReady)
    }

    @Test
    fun handleFavoriteListTest() {
        // given
        val song = Song(songAsJSON)

        //then
        assertFalse(song.isFavorite)
        assertTrue(FreezerModel.favoriteList.isEmpty())

        //when
        FreezerModel.handleFavoriteList(song)

        //then
        assertTrue(song.isFavorite)
        assertEquals(1, FreezerModel.favoriteList.size)

        //when
        FreezerModel.handleFavoriteList(song)

        //then
        assertFalse(song.isFavorite)
        assertTrue(FreezerModel.favoriteList.isEmpty())
    }

    @Test
    fun convertSecondsToMinutesTest() {
        // given
        val song = Song(songAsJSON)

        //then
        assertEquals("5 m 25 s", FreezerModel.convertSecondsToMinutes(song))
    }


}