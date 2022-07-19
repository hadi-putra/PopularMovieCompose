package com.tbi.moviecompose.ui.movie

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.google.accompanist.insets.ui.Scaffold
import com.tbi.moviecompose.data.Result
import com.tbi.moviecompose.R
import com.tbi.moviecompose.data.network.Api
import com.tbi.moviecompose.data.network.response.Cast
import com.tbi.moviecompose.data.network.response.MovieResponse
import com.tbi.moviecompose.ui.components.BackdropImage
import com.tbi.moviecompose.ui.components.DetailAppBar
import com.tbi.moviecompose.ui.components.RatingProgress
import com.tbi.moviecompose.ui.components.bodyWidth
import com.tbi.moviecompose.util.copy
import com.tbi.moviecompose.util.getDollarCurrency
import com.tbi.moviecompose.util.rateColor
import java.util.*

@Composable
fun MovieDetail(
    viewModel: MovieDetailViewModel = hiltViewModel(),
    openMovieDetail: ((Int)-> Unit)? = null,
    navigateUp: ()-> Unit,
){
    val scaffoldState = rememberScaffoldState()
    val listState = rememberLazyListState()
    val detail by remember {
        viewModel.movieDetail
    }
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            var appBarHeight by remember { mutableStateOf(0) }
            val showAppBarBg by remember {
                derivedStateOf{
                    val visibleItemsInfo = listState.layoutInfo.visibleItemsInfo
                    when{
                        visibleItemsInfo.isEmpty() -> false
                        appBarHeight <= 0 -> false
                        else -> {
                            val firstVisibleItemInfo = visibleItemsInfo[0]
                            when {
                                firstVisibleItemInfo.index > 0 -> true
                                else -> (firstVisibleItemInfo.size + firstVisibleItemInfo.offset) <= appBarHeight
                            }
                        }
                    }
                }
            }
            DetailAppBar(
                title = detail.detail?.title ?: stringResource(id = R.string.no_result),
                showAppBarBackground = showAppBarBg,
                modifier = Modifier
                    .fillMaxWidth()
                    .onSizeChanged { appBarHeight = it.height },
                navigateUp = navigateUp
            ){}
        }
    
        ) { paddingValues -> 
        
        Surface(modifier = Modifier.bodyWidth()) {
            if (detail.detail == null){
                Box(modifier = Modifier.fillMaxSize()){
                    Text(text = stringResource(id = R.string.no_result), style = MaterialTheme.typography.h5)
                }
            } else {
                val cast = remember { viewModel.casts }
                val similar = remember { viewModel.similarMovies }
                val recommendation = remember { viewModel.recommendationMovies }
                LazyColumn(
                    contentPadding = paddingValues.copy(copyTop = false),
                    state = listState,
                    modifier = Modifier.fillMaxSize()
                ) {
                    detail.detail?.let { detail->
                        item {
                            BackdropImage(
                                photo = detail.posterPath,
                                backdrop = detail.backdropPath,
                                title = detail.title,
                                releasedDate = detail.releaseDate,
                                country = detail.productionCountries.first().code,
                                genres = detail.genres.map { it.name },
                                duration = detail.runtime,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clipToBounds()
                            )

                        }
                        item { Divider() }
                        item {
                            Stats(
                                vote = detail.voteAverage,
                                status = detail.status,
                                language = detail.originalLanguage,
                                budget = detail.budget,
                                revenue = detail.revenue,
                                color = detail.voteAverage.rateColor,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp, vertical = 8.dp)
                            )
                        }
                        item { Divider() }
                        item {
                            Overview(
                                tagline = detail.tagline,
                                overview = detail.overview,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp, vertical = 8.dp)
                            )
                        }
                        if(cast.value.isNotEmpty()) {
                            item {
                                Casts(
                                    casts = cast.value
                                )
                            }
                            item { Divider() }
                        }
                        item {
                            RelatedMovies(
                                title = R.string.similar_title,
                                movies = similar.value,
                                openMovieDetail = openMovieDetail
                            )
                        }
                        item {
                            RelatedMovies(
                                title = R.string.recommendation_title,
                                movies = recommendation.value,
                                openMovieDetail = openMovieDetail
                            )
                        }
                    }
                }
            }
        }
        
    }
}

@Composable
fun RelatedMovies(
    @StringRes title: Int,
    movies: List<MovieResponse>,
    modifier: Modifier = Modifier,
    openMovieDetail: ((Int) -> Unit)?
) {
    Column(modifier = modifier.padding(top = 16.dp)) {
        Text(text = stringResource(id = title),
            style = MaterialTheme.typography.h5,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp))

        val lazyListState = rememberLazyListState()

        LazyRow(state = lazyListState,
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .height(320.dp)
                .fillMaxWidth()){
            items(movies){
                CardMovieItem(item = it,
                    modifier = modifier
                        .fillParentMaxHeight()
                        .width(128.dp),
                onItemSelected = openMovieDetail)
            }
        }
    }
}


@Composable
fun Casts(casts: List<Cast>, modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Text(text = stringResource(id = R.string.casts_title),
            style = MaterialTheme.typography.h5,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp))
        val lazyListState = rememberLazyListState()
        LazyRow(state = lazyListState,
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .height(240.dp)
                .fillMaxWidth()){
            items(casts){
                CastItem(it,
                    modifier
                        .fillParentMaxHeight()
                        .aspectRatio(0.5f))
            }
        }
    }
}

@Composable
fun CastItem(cast: Cast, modifier: Modifier = Modifier) {
    Column(modifier) {
        AsyncImage(model = "${Api.BASE_POSTER_PATH}${cast.profilePath}",
            contentDescription = cast.name,
            placeholder = painterResource(id = R.mipmap.sapiderman),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .clip(CircleShape))

        Text(text = cast.name, maxLines = 2, modifier = Modifier.padding(top = 8.dp))
        Text(text = cast.character, maxLines = 2, fontWeight = FontWeight.Light)
    }
}

@Composable
fun Overview(tagline: String, overview: String, modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Text(
            text = tagline,
            style = MaterialTheme.typography.subtitle1,
            fontStyle = FontStyle.Italic,
            fontWeight = FontWeight.Light
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(text = stringResource(id = R.string.overview_label),
            style = MaterialTheme.typography.h5,
            fontWeight = FontWeight.Medium)
        Text(text = overview)
    }
}

@Composable
fun Stats(
    vote: Float,
    status: String,
    language: String,
    budget: Long,
    revenue: Long,
    color: Color = Color.Green,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        RatingProgress(
            value = vote/10, text = vote.toString(),
            color = color,
            modifier = Modifier.size(48.dp))

        Column {
            Text(text = stringResource(id = R.string.status_label),
                fontWeight = FontWeight.Medium)
            Text(text = status)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = stringResource(id = R.string.language_label),
                fontWeight = FontWeight.Medium)
            Text(text = Locale(language).displayLanguage)
        }
        Column {
            Text(text = stringResource(id = R.string.budget_label),
                fontWeight = FontWeight.Medium)
            Text(text = budget.getDollarCurrency())
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = stringResource(id = R.string.revenue_label),
                fontWeight = FontWeight.Medium)
            Text(text = revenue.getDollarCurrency())
        }
    }
}