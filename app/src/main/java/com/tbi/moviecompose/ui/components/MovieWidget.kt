package com.tbi.moviecompose.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tbi.moviecompose.ui.theme.MovieComposeTheme

@Composable
fun FavoriteButton(isFavorite: Boolean, onFavoriteChange: (Boolean)->Unit){

    IconToggleButton(checked = isFavorite, onCheckedChange = onFavoriteChange) {
        Icon(imageVector = if(isFavorite) Icons.Filled.Favorite else Icons.Default.FavoriteBorder,
            contentDescription = "Favorite button",
            tint = MaterialTheme.colors.secondary)
    }

}

@Composable
fun RatingProgress(modifier: Modifier = Modifier,
              value: Float = 0f, 
              color: Color = Color.Green, 
              backgroundColor: Color = Color.Black, 
              textColor: Color = Color.White, 
              text: String){
    
    Box(modifier = modifier, contentAlignment = Alignment.Center ){
        CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
            CircularProgressIndicator(
                color = color.copy(alpha = 0.3f),
                strokeWidth = 2.dp,
                progress = 1f,
                modifier = Modifier
                    .fillMaxSize()
                    .background(backgroundColor, CircleShape)
            )
        }
        CircularProgressIndicator(
            color = color,
            strokeWidth = 2.dp,
            progress = value,
            modifier = Modifier.fillMaxSize()
        )
        Text(text = text, style = MaterialTheme.typography.h6, color = textColor)
    }
    
}

@Preview(name = "favorite button")
@Composable
fun FavoritePreview(){
    MovieComposeTheme {
        FavoriteButton(isFavorite = true) {}
    }
}

@Preview(name = "Rating")
@Composable
fun RatingPreview(){
    MovieComposeTheme {
        RatingProgress(text ="5", value = 0.5f, modifier = Modifier.size(32.dp))
    }
}