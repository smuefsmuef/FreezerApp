package fhnw.emoba.freezerapp

import Album
import org.json.JSONObject
import org.junit.Test

import org.junit.Assert.*

class AlbumUnitTest {

    private val albumAsString = """
            {
              "id": "1234",
              "title": "It's a bloody sunday",
              "tracklist": "https://test.url",        
              "cover_small": "https://test.url.jpg",
              "artist": {
              "name": "Eminem"
              }  
            }
    """.trimIndent()

    @Test
    fun testConstructor(){
        //given
        val albumAsJSON = JSONObject(albumAsString)

        //when
        val album = Album(albumAsJSON)

        //then
        with(album) {
            assertEquals(1234, id)
            assertEquals("It's a bloody sunday", title)
            assertEquals("https://test.url", tracklist)
            assertEquals("https://test.url.jpg", cover_small)
            assertEquals("Eminem", artist)
        }
    }

}