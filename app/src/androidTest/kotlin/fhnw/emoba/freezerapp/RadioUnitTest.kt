package fhnw.emoba.freezerapp

import Radio
import org.json.JSONObject
import org.junit.Test

import org.junit.Assert.*

class RadioUnitTest {

    private val radioAsString = """
            {
              "id": "1234",
              "title": "It's a bloody sunday",
              "tracklist": "https://test.url",            
              "picture_small": "https://test.url.jpg"
            }
    """.trimIndent()

    @Test
    fun testConstructor() {
        //given
        val radioAsJSON = JSONObject(radioAsString)

        //when
        val radio = Radio(radioAsJSON)

        //then
        with(radio) {
            assertEquals(1234, id)
            assertEquals("It's a bloody sunday", title)
            assertEquals("https://test.url", tracklist)
            assertEquals("https://test.url.jpg", picture_small)
        }
    }


}