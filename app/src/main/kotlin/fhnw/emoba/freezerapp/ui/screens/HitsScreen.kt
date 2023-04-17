package fhnw.emoba.freezerapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import fhnw.emoba.freezerapp.model.FreezerModel
import androidx.compose.foundation.Image
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.draw.clip
import fhnw.emoba.R
import fhnw.emoba.modules.module04.beers_solution.ui.theme.MaterialAppTheme


@Composable
fun HitsScreen(model: FreezerModel) {
    Body(model = model)
}

@Composable
private fun Body(
    model: FreezerModel
) {
    with(model) {
        with(model) {
            MaterialAppTheme(
                darkTheme
            ) {
                if (favoriteList.isEmpty()) {
                    Card {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .fillMaxHeight()
                                .padding(30.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Image(
                                painterResource(R.drawable.background),
                                contentDescription = "",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clip(RoundedCornerShape(5.dp))
                            )
                            Text(
                                text = "Welcome!",
                                style = MaterialTheme.typography.headlineLarge,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                    Divider()
                } else {
                    Row {
                        LazyColumn(modifier = Modifier.fillMaxSize()) {
                            items(items = favoriteList, key = { it.id }) { item ->
                                SongListItem(item)
                            }
                        }
                    }
                }

            }
        }
    }
}

