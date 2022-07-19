package com.tbi.moviecompose.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.tbi.moviecompose.R
import com.tbi.moviecompose.data.network.Api
import com.tbi.moviecompose.util.buildDottedString
import com.tbi.moviecompose.util.toHourDuration

@Composable
fun BackdropImage(photo: String,
                  backdrop: String,
                  title: String,
                  releasedDate: String,
                  country: String,
                  genres: List<String>,
                  duration: Int,
                  modifier: Modifier = Modifier) {
    Surface(modifier = modifier) {
        Box(modifier = Modifier) {
            AsyncImage(
                model = "${Api.BASE_BACKDROP_PATH}$backdrop",
                contentDescription = "backdrop",
                placeholder = painterResource(id = R.mipmap.sapiderman),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .aspectRatio(8 / 5f))

            Box(modifier = Modifier
                .padding(top = 150.dp)
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, bottom = 12.dp)
                .aspectRatio(8 / 5f),
                contentAlignment = Alignment.BottomStart){

                Row(modifier = Modifier.fillMaxWidth()){
                    AsyncImage(
                        model = "${Api.BASE_POSTER_PATH}$photo",
                        contentDescription = "poster",
                        placeholder = painterResource(id = R.mipmap.sapiderman),
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .height(180.dp)
                            .aspectRatio(2 / 3f))

                    Column(modifier = Modifier.padding(start = 8.dp, top = 72.dp)) {
                        Text(
                            text = title,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis,
                            style = MaterialTheme.typography.h6
                        )

                        CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                            val tmp = mutableListOf("$releasedDate ($country)")
                            if (genres.isNotEmpty())
                                tmp.addAll(genres)
                            tmp.add(duration.toHourDuration())
                            Text(
                                tmp.buildDottedString().toString(),
                                style = MaterialTheme.typography.overline,
                                maxLines = 3)
                        }
                    }
                }
            }
        }
    }
}