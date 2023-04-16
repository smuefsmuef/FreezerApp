package fhnw.emoba.freezerapp

import Album
import AlbumSong
import Radio
import Song
import android.graphics.BitmapFactory
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import fhnw.emoba.freezerapp.model.Screen
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import javax.net.ssl.HttpsURLConnection


class DeezerApi {

    companion object {

        fun search(term: String, searchType: String): JSONArray {
            val url = URL("https://api.deezer.com/search/$searchType?q=$term")
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "GET"

            val responseCode = connection.responseCode

            if (responseCode == HttpsURLConnection.HTTP_OK) {
                val inputStream = BufferedReader(InputStreamReader(connection.inputStream))
                val response = StringBuffer()

                var inputLine: String?
                while (inputStream.readLine().also { inputLine = it } != null) {
                    response.append(inputLine)
                }
                inputStream.close()

                val json = JSONObject(response.toString())
                return json.getJSONArray("data")
            } else {
                throw Exception("Failed to load album from Deezer API: HTTP error code $responseCode")
            }
        }

        fun searchTrack(term: String): List<Song> {
            val dataArray = search(term, "track")
            val entries = mutableListOf<Song>()
            for (i in 0 until dataArray.length()) {
                entries.add(Song(dataArray.getJSONObject(i)))
            }
            return entries
        }

        fun searchRadio(term: String): List<Radio> {
            val dataArray = search(term, "radio")
            val entries = mutableListOf<Radio>()
            for (i in 0 until dataArray.length()) {
                entries.add(Radio(dataArray.getJSONObject(i)))
            }
            return entries
        }

        fun searchAlbum(term: String): List<Album> {
            val dataArray = search(term, "album")
            val entries = mutableListOf<Album>()
            for (i in 0 until dataArray.length()) {
                entries.add(Album(dataArray.getJSONObject(i)))
            }
            return entries
        }

        fun searchTrackList(id: Int, searchType: String): JSONArray {
            val url = URL("https://api.deezer.com/$searchType/${id}/tracks")
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "GET"

            val responseCode = connection.responseCode

            if (responseCode == HttpURLConnection.HTTP_OK) {
                val inputStream = BufferedReader(InputStreamReader(connection.inputStream))
                val response = StringBuffer()

                var inputLine: String?
                while (inputStream.readLine().also { inputLine = it } != null) {
                    response.append(inputLine)
                }
                inputStream.close()
                val json = JSONObject(response.toString())
                return json.getJSONArray("data")
            } else {
                throw Exception("Failed to load album from Deezer API: HTTP error code $responseCode")
            }
        }

        fun searchAlbumTrackList(album: Album): List<AlbumSong> {
            val dataArray = searchTrackList(album.id, "album")
            val entries = mutableListOf<AlbumSong>()
            for (i in 0 until dataArray.length()) {
                entries.add(AlbumSong(dataArray.getJSONObject(i)))
            }
            return entries
        }

        fun searchRadioTrackList(radio: Radio): List<Song> {
            val dataArray = searchTrackList(radio.id, "radio")
            val entries = mutableListOf<Song>()
            for (i in 0 until dataArray.length()) {
                entries.add(Song(dataArray.getJSONObject(i)))
            }
            return entries
        }

        fun loadImage(urli: String): ImageBitmap {
            try {
                val url = URL(urli)
                val conn = url.openConnection() as HttpsURLConnection
                conn.connect()
                val inputStream = conn.inputStream
                val allBytes = inputStream.readBytes()
                inputStream.close()

                return BitmapFactory.decodeByteArray(
                    allBytes, 0, allBytes.size
                ).asImageBitmap()


            } catch (e: Exception) {
                return ImageBitmap(3, 7)
            }
        }


    }
}