package fhnw.emoba.freezerapp.ui.screens

import Album
import AlbumSong
import Radio
import Song
import android.annotation.SuppressLint
import android.view.KeyEvent.KEYCODE_ENTER
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.foundation.Image
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import fhnw.emoba.freezerapp.model.FreezerModel
import fhnw.emoba.freezerapp.model.Screen
import java.util.*
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import fhnw.emoba.freezerapp.model.FreezerModel.convertSecondsToMinutes
import fhnw.emoba.freezerapp.model.FreezerModel.fromStart
import fhnw.emoba.freezerapp.model.FreezerModel.handleFavoriteList
import fhnw.emoba.freezerapp.model.FreezerModel.pausePlayer
import fhnw.emoba.freezerapp.model.FreezerModel.playNextSong
import fhnw.emoba.freezerapp.model.FreezerModel.isPlayerReady
import fhnw.emoba.freezerapp.model.FreezerModel.radioSongListSearch
import fhnw.emoba.freezerapp.model.FreezerModel.songListSearch
import fhnw.emoba.freezerapp.model.FreezerModel.startPlayer
import fhnw.emoba.modules.module04.beers_solution.ui.theme.MaterialAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DefaultScreen(
    model: FreezerModel, screen: Screen
) {
    with(model) {
        MaterialAppTheme(
            darkTheme
        ) {
            Scaffold(content = { DefaultBody(screen, it, model) })
        }
    }
}

@SuppressLint("SuspiciousIndentation")
@Composable
fun DefaultBody(screen: Screen, paddingValues: PaddingValues, model: FreezerModel) {
    ConstraintLayout(
        Modifier
            .padding(paddingValues)
            .fillMaxSize()
    ) {
        val (searchForm, results, footer) = createRefs()
        SearchForm(model, Modifier.constrainAs(searchForm) {
            start.linkTo(parent.start, 30.dp)
            top.linkTo(parent.top, 30.dp)
            end.linkTo(parent.end, 30.dp)
            width = Dimension.fillToConstraints
        })
        SearchResults(screen, model, Modifier.constrainAs(results) {
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            top.linkTo(searchForm.bottom, 30.dp)
            bottom.linkTo(footer.top, 5.dp)
            width = Dimension.fillToConstraints
            height = Dimension.fillToConstraints
        })
        Footer(model, Modifier.constrainAs(footer) {
            bottom.linkTo(parent.bottom)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            width = Dimension.fillToConstraints
        })
    }
}

@Composable
private fun SearchResults(
    screen: Screen, model: FreezerModel, modifier: Modifier
) {
    with(model) {
        Box(
            modifier = modifier, contentAlignment = Alignment.Center
        ) {
            if (isLoading) {
                CircularProgressIndicator()
            } else {

                if (currentSongList.isEmpty() && currentAlbumList.isEmpty() && currentRadioList.isEmpty()) {
                    Text(
                        text = "no results", style = MaterialTheme.typography.titleSmall
                    )
                } else {
                    if (screen == Screen.SONGS) {
                        Divider(modifier = Modifier.align(Alignment.TopStart))
                        LazyColumn(modifier = Modifier.fillMaxSize()) {
                            items(items = currentSongList, key = { it.id }) { item ->
                                SongListItem(item)
                            }
                        }
                    }
                    if (screen == Screen.ALBUMS) {
                        if (isListView) CustomDialog(setShowDialog = {
                            isListView = it
                        }, Screen.ALBUMS, model)
                        Divider(modifier = Modifier.align(Alignment.TopStart))
                        LazyColumn(modifier = Modifier.fillMaxSize()) {
                            items(items = currentAlbumList, key = { it.id }) { item ->
                                AlbumListItem(item)
                            }
                        }
                    }
                    if (screen == Screen.RADIO) {
                        if (isListView) CustomDialog(setShowDialog = {
                            isListView = it
                        }, Screen.RADIO, model)
                        Divider(modifier = Modifier.align(Alignment.TopStart))
                        LazyColumn(modifier = Modifier.fillMaxSize()) {
                            items(items = currentRadioList, key = { it.id }) { item ->
                                RadioListItem(item)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun Footer(model: FreezerModel, modifier: Modifier) {
    with(model) {
        Text(
            text = "Petra Kohler 2023",
            style = MaterialTheme.typography.labelSmall,
            modifier = modifier
                .background(MaterialTheme.colorScheme.primary)
                .padding(horizontal = 15.dp, vertical = 5.dp)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SongListItem(song: Song) {
    with(song) {
        Column {
            ListItem(headlineText = { Text(title) },
                overlineText = { Text(artist.name) },
                supportingText = { Text(convertSecondsToMinutes(song)) },
                trailingContent = {
                    Column(
                        modifier = Modifier
                            .width(80.dp)
                            .padding(2.dp),
                        verticalArrangement = Arrangement.spacedBy(5.dp)
                    ) {
                        with(song) {
                            Row(modifier = Modifier.fillMaxWidth(), Arrangement.SpaceBetween) {
                                Column {
                                    PlayerButtons(song)
                                }
                            }
                            IconButton(modifier = Modifier.fillMaxWidth(),
                                onClick = { handleFavoriteList(song) }) {
                                if (isFavorite) {
                                    Icon(
                                        imageVector = Icons.Filled.Favorite,
                                        tint = Color.Blue,
                                        contentDescription = "favorite"
                                    )
                                } else {
                                    Icon(
                                        imageVector = Icons.Default.FavoriteBorder,
                                        contentDescription = "not favorite"
                                    )
                                }
                            }
                        }
                    }
                },
                leadingContent = {
                    SongImage(song)
                })
            Divider()
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SongListItemCompact(song: AlbumSong) {
    with(song) {
        Column {
            ListItem(headlineText = { Text(title) }, overlineText = { Text(artist.name) })
            Divider()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AlbumListItem(album: Album) {
    with(album) {
        Column {
            ListItem(headlineText = { Text(title) },
                overlineText = { Text(artist) },
                leadingContent = {
                    AlbumImage(album)
                },
                modifier = Modifier.clickable { songListSearch(album) })
            Divider()
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun RadioListItem(radio: Radio) {
    with(radio) {
        Column {
            ListItem(headlineText = { Text(title) },
                overlineText = { Text(radio.id.toString()) },
                leadingContent = {
                    RadioImage(radio)
                },
                modifier = Modifier.clickable { radioSongListSearch(radio) })
            Divider()
        }
    }
}


@Composable
private fun SongImage(song: Song) {
    with(song) {
        Row {
            Image(
                bitmap = imageBitmap,
                contentDescription = "Cover",
                modifier = Modifier
                    .height(80.dp)
                    .width(80.dp)
                    .shadow(4.dp),
                contentScale = ContentScale.FillWidth
            )
        }
    }
}

@Composable
private fun AlbumImage(album: Album) {
    with(album) {
        Row {
            Image(
                bitmap = imageBitmapAlbum,
                contentDescription = "Cover",
                modifier = Modifier
                    .height(80.dp)
                    .width(80.dp)
                    .shadow(4.dp),
                contentScale = ContentScale.FillWidth
            )
        }
    }
}

@Composable
private fun RadioImage(radio: Radio) {
    with(radio) {
        Row {
            Image(
                bitmap = imageBitmapRadio,
                contentDescription = "Cover",
                modifier = Modifier
                    .height(80.dp)
                    .width(80.dp)
                    .shadow(4.dp),
                contentScale = ContentScale.FillWidth
            )
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
private fun SearchForm(model: FreezerModel, modifier: Modifier) {
    with(model) {
        val keyboard = LocalSoftwareKeyboardController.current
        OutlinedTextField(
            value = filter,
            onValueChange = { filter = it },
            placeholder = { Text("Search...") },
            modifier = modifier.onKeyEvent {
                if (it.nativeKeyEvent.keyCode == KEYCODE_ENTER) {
                    filter = filter.replace("\n", "", ignoreCase = true)
                    launchSearch()
                    keyboard?.hide()
                }
                false
            },
            trailingIcon = {
                IconButton(onClick = {
                    keyboard?.hide()
                    filter = ""
                    launchSearch()
                }) {
                    Icon(Icons.Filled.Clear, "delete")
                }
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done, autoCorrect = false, keyboardType = KeyboardType.Ascii
            ),

            keyboardActions = KeyboardActions(onSearch = {
                keyboard?.hide()
                launchSearch()
            }),

            )
    }
}


@Composable
private fun PlayerButtons(song: Song) {
    with(song) {
        Box(
            modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center
        ) {
            if (!isPlayerReady && song.isPlaying) {
                PlayFromStartButton(
                    song,
                    Modifier
                        .align(Alignment.CenterStart)
                        .width(30.dp)
                        .height(30.dp)
                )
            }
            PlayPauseButton(song, Modifier.align(Alignment.Center))
            if (!isPlayerReady && song.isPlaying) {
                PlayNextButton(
                    song,
                    Modifier
                        .align(Alignment.BottomEnd)
                        .width(30.dp)
                        .height(30.dp)
                )
            }

        }
    }
}

@Composable
private fun PlayPauseButton(song: Song, modifier: Modifier) {
    with(song) {
        val enabled = true
        if (isPlayerReady) {
            IconButton(
                onClick = { startPlayer(song) }, modifier = Modifier
                    .background(
                        SolidColor(Color.LightGray),
                        shape = CircleShape,
                        alpha = if (enabled) 1.0f else 0.3f
                    )
                    .size(30.dp)
                    .then(modifier), enabled = enabled
            ) {
                Icon(
                    Icons.Filled.PlayArrow,
                    "",
                    tint = if (enabled) Color.Black else Color.Gray,
                    modifier = Modifier
                        .size(30.dp)
                        .then(modifier)
                )
            }
        } else if (song.isPlaying) {
            IconButton(
                onClick = { pausePlayer(song) },
                modifier = Modifier
                    .background(Color.Green, shape = CircleShape)
                    .size(30.dp)
                    .then(modifier)
            ) {
                Icon(Icons.Filled.Pause, "", modifier = Modifier.size(30.dp))
            }
        }
    }
}

@Composable
private fun PlayFromStartButton(song: Song, modifier: Modifier) {
    with(song) {
        IconButton(onClick = { fromStart() }, modifier = modifier) {
            Icon(Icons.Filled.SkipPrevious, "Back", modifier = Modifier.size(30.dp))
        }
    }
}

@Composable
private fun PlayNextButton(song: Song, modifier: Modifier) {
    with(song) {
        IconButton(onClick = { playNextSong(song) }, modifier = modifier) {
            Icon(Icons.Filled.SkipNext, "Next", modifier = Modifier.size(30.dp))
        }
    }
}

@Composable
fun FAB(model: FreezerModel) {
    with(model) {
        FloatingActionButton(onClick = { toggleTheme() }) {
            Icon(Icons.Filled.InvertColors, "Color")
        }
    }
}


@Preview(device = Devices.PIXEL_4)
@Composable
private fun DefaultBodyPreview() {
    DefaultBody(Screen.HITS, PaddingValues(0.dp), FreezerModel)
}
