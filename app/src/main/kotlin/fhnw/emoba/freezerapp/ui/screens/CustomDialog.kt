package fhnw.emoba.freezerapp.ui.screens

import android.R
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import fhnw.emoba.freezerapp.model.FreezerModel
import fhnw.emoba.freezerapp.model.Screen
import fhnw.emoba.modules.module04.beers_solution.ui.theme.MaterialAppTheme

@Composable
fun CustomDialog(setShowDialog: (Boolean) -> Unit, screen: Screen, model: FreezerModel) {
    with(model) {
        MaterialAppTheme(
            darkTheme
        ) {
            Dialog(onDismissRequest = { setShowDialog(false)  }) {
                Surface(
                    shape = RoundedCornerShape(16.dp),
                ) {
                    Box(
                        contentAlignment = Alignment.TopEnd
                    ) {
                        Column(modifier = Modifier.padding(20.dp)) {

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(imageVector = Icons.Filled.Cancel,
                                    contentDescription = "",
                                    tint = colorResource(R.color.darker_gray),
                                    modifier = Modifier
                                        .width(30.dp)
                                        .height(30.dp)
                                        .clickable { setShowDialog(false) })

                            }
                            if (screen == Screen.ALBUMS) {
                                Spacer(modifier = Modifier.height(20.dp))
                                LazyColumn(modifier = Modifier.fillMaxSize()) {
                                    items(items = currentAlbumSonglist, key = { it.id }) { item ->
                                        SongListItemCompact(item)
                                    }
                                }
                            } else if (screen == Screen.RADIO) {
                                Spacer(modifier = Modifier.height(20.dp))
                                LazyColumn(modifier = Modifier.fillMaxSize()) {
                                    items(items = currentRadioSonglist, key = { it.id }) { item ->
                                        SongListItem(item)
                                    }
                                }
                            }


                        }
                    }
                }
            }
        }
    }
}