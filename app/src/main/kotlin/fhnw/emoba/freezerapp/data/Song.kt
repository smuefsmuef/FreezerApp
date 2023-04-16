import android.graphics.Bitmap
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.asImageBitmap
import org.json.JSONObject


class Song(json: JSONObject) {
    val id = json.getInt("id")
    val title: String = json.getString("title")
    val duration = json.getInt("duration")
    val preview = json.getString("preview")
    var isFavorite by mutableStateOf(false)
    var isPlaying by mutableStateOf(false)
    val artist = Artist(json.getJSONObject("artist").getString("name"),
        json.getJSONObject("artist").getString("picture_small"))
    var imageBitmap by mutableStateOf(Bitmap.createBitmap(30,30,Bitmap.Config.ALPHA_8).asImageBitmap())

    data class Artist (val name: String, val picture_small: String?){
    }

}

