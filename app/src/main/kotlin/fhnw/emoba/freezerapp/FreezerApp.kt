package fhnw.emoba.freezerapp

import androidx.activity.ComponentActivity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import fhnw.emoba.EmobaApp
import fhnw.emoba.freezerapp.model.FreezerModel
import fhnw.emoba.freezerapp.ui.AppUI
import fhnw.emoba.freezerapp.ui.screens.AlbumsScreen

object FreezerApp : EmobaApp {

    private lateinit var model: FreezerModel

    override fun initialize(activity: ComponentActivity) {
        model = FreezerModel
    }
    @Composable
    override fun CreateUI() {
        AppUI(FreezerModel)
        model.darkTheme = isSystemInDarkTheme()
    }

}