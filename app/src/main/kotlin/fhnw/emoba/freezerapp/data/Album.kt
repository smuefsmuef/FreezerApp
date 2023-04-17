import android.graphics.Bitmap
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.asImageBitmap
import org.json.JSONObject


class Album(json: JSONObject) {
    val id = json.getInt("id")
    val title: String = json.getString("title")
    val cover_small = json.getString("cover_small")
    val tracklist = json.getString("tracklist")
    val artist = json.getJSONObject("artist").getString("name")
    var imageBitmapAlbum by mutableStateOf(Bitmap.createBitmap(30,30, Bitmap.Config.ALPHA_8).asImageBitmap())

}

