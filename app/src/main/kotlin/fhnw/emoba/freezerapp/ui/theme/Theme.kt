package fhnw.emoba.modules.module04.beers_solution.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val darkColors = darkColorScheme(
    //Background colors
    primary = Color(0xFF86FCAB),
    primaryContainer = Color(0xFF5B9B6F),
    secondary = Color(0xFF03DAC6),
    secondaryContainer = Color(0xFF03DAC6),
    background = Color(0xFF0C2422),
    surface = Color(0xFF649B97),
    error = Color(0xFFCF6679),

    //Typography and icon colors
    onPrimary = Color.Black,
    onSecondary = Color.Black,
    onBackground = Color.White,
    onSurface = Color.White,
    onError = Color.Black,
)

private val lightColors = lightColorScheme(
    //Background colors
    primary = tealA700,
    primaryContainer = tealA400,
    secondary = amber500,

    secondaryContainer = Color(0xFF03DAC6),
    background = Color.White,
    surface = Color.White,
    error = Color(0xFFB00020),

    //Typography and icon colors
    onPrimary = gray900,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    onError = Color.White,
)

@Composable
//fun MaterialAppTheme(darkTheme: Boolean, bigTypo: Boolean, content: @Composable() () -> Unit) {
fun MaterialAppTheme(darkTheme: Boolean, content: @Composable() () -> Unit) {
    MaterialTheme(
        colorScheme = if (darkTheme) darkColors else lightColors,
        typography = typography,
        shapes = shapes,
        content = content
    )
}