package fhnw.emoba.freezerapp.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import fhnw.emoba.freezerapp.model.FreezerModel
import fhnw.emoba.freezerapp.model.Screen

@Composable
fun RadioScreen(model: FreezerModel) {
    DefaultScreen(model = model, screen = Screen.RADIO)
}
@Preview(device = Devices.PIXEL_4)
@Composable
private fun ScreenPreview() {
    RadioScreen(model = FreezerModel)
}