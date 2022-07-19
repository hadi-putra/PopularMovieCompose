package com.tbi.moviecompose.ui.components

import androidx.annotation.StringRes
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Tv
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Tv
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.stringResource
import com.google.accompanist.insets.ui.BottomNavigation
import com.tbi.moviecompose.R
import com.tbi.moviecompose.ui.main.Screen

@Composable
fun MainBottomNavigation(
    modifier: Modifier = Modifier,
    selectedScreen: Screen,
    onTabClicked: (Screen) -> Unit
) {
    BottomNavigation(contentPadding = WindowInsets.navigationBars.asPaddingValues(),
        modifier = modifier) {

        MainBottomNavItem.values().forEach { item ->
            val selected = selectedScreen == item.screen
            BottomNavigationItem(
                selected = selected,
                onClick = { onTabClicked(item.screen) } ,
                label = { Text(text = stringResource(id = item.title))},
                icon = {
                    val painter = rememberVectorPainter(image = item.icon)
                    val selectedPainter = rememberVectorPainter(image = item.iconSelected)

                    Crossfade(targetState = selected) {
                        Icon(painter = if(it) selectedPainter else painter, contentDescription = stringResource(
                            id = item.title
                        ))
                    }
                })
        }
    }
}

@Composable
fun MainNavigationRail(
    modifier: Modifier = Modifier,
    selectedScreen: Screen,
    onTabClicked: (Screen) -> Unit
){
    Surface(
        color = MaterialTheme.colors.surface,
        modifier = modifier) {
        NavigationRail(
            backgroundColor = Color.Transparent,
            contentColor = MaterialTheme.colors.onSurface,
            modifier = Modifier.padding(WindowInsets.systemBars
                .only(WindowInsetsSides.Start+ WindowInsetsSides.Vertical).asPaddingValues()))
        {
            MainBottomNavItem.values().forEach { item ->
                val selected = selectedScreen == item.screen
                NavigationRailItem(selected = selected,
                    onClick = { onTabClicked(item.screen) },
                    icon = {
                        val painter = rememberVectorPainter(image = item.icon)
                        val selectedPainter = rememberVectorPainter(image = item.iconSelected)

                        Crossfade(targetState = selected) {
                            Icon(painter = if(it) selectedPainter else painter, contentDescription = stringResource(
                                id = item.title
                            ))
                        }
                    },
                    label = { Text(text = stringResource(id = item.title)) },
                    alwaysShowLabel = false)
            }
        }
    }
}

@Immutable
enum class MainBottomNavItem(
    val screen: Screen,
    @StringRes val title: Int,
    val icon: ImageVector,
    val iconSelected: ImageVector
){
    MOVIE(Screen.Movie, R.string.menu_movie, Icons.Outlined.Home, Icons.Default.Home),
    TV(Screen.TV, R.string.menu_tv, Icons.Outlined.Tv, Icons.Default.Tv),
    SEARCH(Screen.Search, R.string.search_menu, Icons.Outlined.Search, Icons.Default.Search)
}