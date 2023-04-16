package fhnw.emoba.freezerapp.ui

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.SkipPrevious
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fhnw.emoba.R
import fhnw.emoba.freezerapp.model.FreezerModel
import fhnw.emoba.freezerapp.model.FreezerModel.currentScreen
import fhnw.emoba.freezerapp.model.Screen
import fhnw.emoba.freezerapp.ui.screens.*
import fhnw.emoba.modules.module04.beers_solution.ui.theme.MaterialAppTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppUI(model: FreezerModel) {
    with(model) {
        MaterialAppTheme(
            darkTheme
        ) {
        Scaffold(
                topBar = { Bar(model) },
                content = { padding ->
                    Body(model, padding)
                },
                floatingActionButton = {FAB(model)},

                )

        }
    }
}

@Composable
private fun Bar(model: FreezerModel) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .background(Color.Transparent),
        horizontalArrangement = Arrangement.Center,
    ) {

        Image(painterResource(R.drawable.deezerlogo), FreezerModel.title)
    }
}

@Composable
private fun Body(model: FreezerModel, padding: PaddingValues) {
    with(model) {

        Column(modifier = Modifier.padding(padding)) {
            TabRow(selectedTabIndex = currentScreen.ordinal) {


                for (tab in Screen.values()) {
                    if(tab.mainTab){
                        Tab(text = { Text(tab.title) },
                            selected = tab == currentScreen,
                            onClick = { currentScreen = tab }
                        )
                    }

                }
            }
            ContentBox(model = model)
        }
    }
}

@Composable
private fun ContentBox(model: FreezerModel) {
    Crossfade(targetState = currentScreen) { screen ->
        when (screen) {
            Screen.HITS -> {
                HitsScreen(model)
            }
            Screen.SONGS -> {
                SongsScreen(model)
            }
            Screen.ALBUMS -> {
                AlbumsScreen(model)
            }
            Screen.RADIO -> {
                RadioScreen(model)
            }

        }
    }
}


@Preview
@Composable
fun Preview() {
    AppUI(FreezerModel)
}
