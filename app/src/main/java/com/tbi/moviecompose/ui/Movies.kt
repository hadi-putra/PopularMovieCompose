package com.tbi.moviecompose.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.AsyncImage
import com.google.accompanist.insets.ui.Scaffold
import com.tbi.moviecompose.R
import com.tbi.moviecompose.ui.components.FavoriteButton
import com.tbi.moviecompose.ui.components.RatingProgress
import com.tbi.moviecompose.ui.theme.MovieComposeTheme

internal enum class ShowType(
    @StringRes val title: Int
){
    MOVIE(R.string.title_movie),
    TV(R.string.title_tv)
}

@Composable
fun Movies(){

}

@Composable
private fun ShowList(
    showType: ShowType,
    onItemSelected: (Int)-> Unit,
    onFavoriteClick: ((Int, Boolean) -> Unit)
){
    Scaffold(
        topBar = {AppBar( modifier = Modifier.fillMaxWidth(), title = stringResource(id = showType.title))},
        modifier = Modifier.fillMaxSize()
    ) { paddingValues ->
        val lazyGridState = rememberLazyGridState()
        val movies = (1..20).toList()
        LazyVerticalGrid(
            columns = GridCells.Adaptive(128.dp),
            state = lazyGridState,
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = paddingValues
        ){
            items(movies){
                CardItem(item  = it, modifier = Modifier.fillMaxWidth(), onItemSelected = onItemSelected)
            }
        }
    }
}

const val IMAGE_DUMMY = "https://www.themoviedb.org/t/p/w440_and_h660_face/9Gtg2DzBhmYamXBS1hKAhiwbBKS.jpg"

@Composable
private fun CardItem(
    modifier: Modifier = Modifier,
    item: Int,
    onItemSelected: ((Int) -> Unit)? = null,
    onFavoriteClick: ((Int, Boolean) -> Unit)? = null
){
    
    Card(modifier = onItemSelected?.let { modifier.clickable { onItemSelected(item) } } ?: modifier,
        shape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp)) {

        ConstraintLayout(modifier = Modifier.fillMaxWidth()) {
            val (image, rating, title, release) = createRefs()

            AsyncImage(model = IMAGE_DUMMY,
                contentDescription = "Image of Dr Strange",
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

            val value = 50
            RatingProgress(modifier = Modifier.size(32.dp).constrainAs(rating){
                top.linkTo(image.bottom)
                bottom.linkTo(image.bottom)
                start.linkTo(parent.start, 8.dp)
            }, text = value.toString(), value = value/100f, color = if (value > 50) Color.Green else Color.Red)

            Row(
                modifier = Modifier.constrainAs(title){
                    top.linkTo(rating.bottom, 12.dp)
                    start.linkTo(parent.start, 8.dp)
                    end.linkTo(parent.end, 8.dp)
                    width = Dimension.fillToConstraints
                },
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "item.title",
                    style = MaterialTheme.typography.h6,
                    modifier = Modifier.align(Alignment.CenterVertically))

                FavoriteButton(isFavorite = true, onFavoriteChange = {
                    onFavoriteClick?.let { click ->
                        click(item, it)
                    }
                })
            }

            Text(text = "item.release+genre",
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

@Composable
private fun AppBar(
    modifier: Modifier = Modifier,
    title: String
){

}

@Preview(name = "Show Item")
@Composable
fun ItemPreview(){
    MovieComposeTheme {
        CardItem(item = 1)
    }
}