package com.tbi.moviecompose.ui.movie

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import com.google.accompanist.insets.ui.Scaffold
import com.tbi.moviecompose.R
import com.tbi.moviecompose.data.network.Api
import com.tbi.moviecompose.data.network.response.MovieResponse
import com.tbi.moviecompose.ui.components.FavoriteButton
import com.tbi.moviecompose.ui.components.MainAppBar
import com.tbi.moviecompose.ui.components.RatingProgress
import com.tbi.moviecompose.ui.theme.MovieComposeTheme
import com.tbi.moviecompose.util.items
import com.tbi.moviecompose.util.rateColor
import com.tbi.moviecompose.util.rememberFlowWithLifeCycle

@Composable
fun MovieList(
    openMovieDetails: (movieId: Int)->Unit
){
    Log.i("Movie Screen", "Build Movie Compose")
    MovieList(viewModel = hiltViewModel(), openMovieDetails = openMovieDetails)
}

@Composable
private fun MovieList(
    viewModel: ListMovieViewModel,
    openMovieDetails: (movieId: Int)->Unit
){
    val scaffoldState = rememberScaffoldState()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            MainAppBar(title = stringResource(id = R.string.menu_movie))
        },
        modifier = Modifier.fillMaxSize()
    ) { paddingValues ->
        val movieList = rememberFlowWithLifeCycle(viewModel.pageMovieList).collectAsLazyPagingItems()
        val lazyGridState = rememberLazyGridState()

        LazyVerticalGrid(
            columns = GridCells.Adaptive(128.dp),
            state = lazyGridState,
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = paddingValues
        ) {

            items(items = movieList, key = {it.id}){
                it?.let {
                    CardMovieItem(
                        item = it,
                        modifier = Modifier.fillMaxWidth(),
                        onItemSelected = openMovieDetails
                    )
                }

            }
        }
    }
}

@Composable
fun CardMovieItem(
    modifier: Modifier = Modifier,
    item: MovieResponse,
    onItemSelected: ((Int) -> Unit)? = null,
    onFavoriteClick: ((Int, Boolean) -> Unit)? = null
){

    Card(modifier = onItemSelected?.let { modifier.clickable { onItemSelected(item.id) } } ?: modifier,
        shape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp)) {

        ConstraintLayout(modifier = Modifier.fillMaxWidth()) {
            val (image, rating, title, release) = createRefs()

            AsyncImage(model = "${Api.BASE_POSTER_PATH}${item.posterUri}",
                contentDescription = "Poster of ${item.title}",
                placeholder = painterResource(id = R.mipmap.sapiderman),
                modifier = Modifier
                    .constrainAs(image) {
                        top.linkTo(parent.top)
                        end.linkTo(parent.end)
                        start.linkTo(parent.start)
                    }
                    .aspectRatio(2 / 3f)
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop
            )

            RatingProgress(modifier = Modifier
                .size(32.dp)
                .constrainAs(rating) {
                    top.linkTo(image.bottom)
                    bottom.linkTo(image.bottom)
                    start.linkTo(parent.start, 8.dp)
                }, text = item.voteAverage.toString(), value = item.voteAverage/10, color = item.rateColor)

            Row(
                modifier = Modifier.constrainAs(title){
                    top.linkTo(rating.bottom, 12.dp)
                    start.linkTo(parent.start, 8.dp)
                    end.linkTo(parent.end, 8.dp)
                    width = Dimension.fillToConstraints
                },
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = item.title,
                    style = MaterialTheme.typography.h6,
                    maxLines = 1,
                    modifier = Modifier.align(Alignment.CenterVertically))

                FavoriteButton(isFavorite = true, onFavoriteChange = {
                    onFavoriteClick?.let { click ->
                        click(item.id, it)
                    }
                })
            }

            Text(text = item.releaseDate,
                style = MaterialTheme.typography.subtitle1,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.constrainAs(release){
                    top.linkTo(title.bottom)
                    start.linkTo(title.start)
                    end.linkTo(title.end)
                    bottom.linkTo(parent.bottom, 12.dp)
                    width = Dimension.fillToConstraints
                })
        }
    }
}

@Preview
@Composable
fun MovieListPreview(){
    MovieComposeTheme {
        MovieList{}
    }
}