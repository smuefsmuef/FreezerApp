import android.graphics.Bitmap
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.asImageBitmap
import org.json.JSONObject


class AlbumSong(json: JSONObject) {
    val id = json.getInt("id")
    val title: String = json.getString("title")
    val preview = json.getString("preview")
    var isPlaying by mutableStateOf(false)
    val artist = Artist(json.getJSONObject("artist").getString("name"))

    data class Artist (val name: String){
    }

}

