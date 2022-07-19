package com.tbi.moviecompose.ui.components

import androidx.annotation.FloatRange
import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.insets.ui.TopAppBar
import com.tbi.moviecompose.ui.theme.MovieComposeTheme

@Composable
fun MainAppBar(
    title: String
) {
    TopAppBar(
        contentPadding = WindowInsets.systemBars
            .only(WindowInsetsSides.Horizontal + WindowInsetsSides.Top)
            .asPaddingValues(),
        modifier = Modifier.fillMaxWidth(),
        title = {
            Text(text = title.uppercase(),
                style = MaterialTheme.typography.h4,
                fontSize = 24.sp
            )
        })
}

@Composable
fun DetailAppBar(
    title: String,
    showAppBarBackground: Boolean,
    modifier: Modifier = Modifier,
    navigateUp: () -> Unit,
    onShare: () -> Unit
) {
    val bgColor by animateColorAsState(
        targetValue = when {
            showAppBarBackground -> MaterialTheme.colors.surface
            else -> Color.Transparent
        },
        animationSpec = spring()
    )

    val elevation by animateDpAsState(
        targetValue = when {
            showAppBarBackground -> 4.dp
            else -> 0.dp
        },
        animationSpec = spring()
    )

    TopAppBar(title = {
        Crossfade(showAppBarBackground && title.isNotBlank()) {
            if (it) Text(text = title)
        }
    },
        contentPadding = WindowInsets.systemBars.only(WindowInsetsSides.Horizontal + WindowInsetsSides.Top).asPaddingValues(),
        navigationIcon = {
            IconButton(onClick = navigateUp, modifier = Modifier.animateInExpandable(!showAppBarBackground)) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
            }
        }, actions = {
            IconButton(onClick = onShare, modifier = Modifier.animateInExpandable(!showAppBarBackground)) {
                Icon(imageVector = Icons.Default.Share, contentDescription = "Share")
            }
        }, elevation = elevation,
        backgroundColor = bgColor,
        modifier = modifier
    )
}

fun Modifier.animateInExpandable(
    enabled: Boolean,
    @FloatRange(from = 0.00, to = 1.00) alpha: Float = 0.4f,
    shape: Shape = CircleShape
): Modifier = composed {
    if (enabled){
        Modifier.background(
            color = MaterialTheme.colors.surface.copy(alpha = alpha),
            shape = shape
        )
    } else
        this
}


@Preview
@Composable
fun MainAppBarPreview(){
    MovieComposeTheme {
        MainAppBar(title = "Movie")
    }
}

