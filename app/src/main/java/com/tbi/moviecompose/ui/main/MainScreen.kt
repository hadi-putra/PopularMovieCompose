package com.tbi.moviecompose.ui.main

import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.insets.ui.Scaffold
import com.tbi.moviecompose.ui.components.MainBottomNavigation
import com.tbi.moviecompose.ui.components.MainNavigationRail

@Composable
internal fun MainScreen(){
    val navController = rememberNavController()
    val configuration = LocalConfiguration.current

    val useBottomNavigation by remember {
        derivedStateOf { configuration.smallestScreenWidthDp < 600 }
    }

    Scaffold(bottomBar = {
        if (useBottomNavigation){
            val currentSelectedTab by navController.currentScreenState()
            MainBottomNavigation(selectedScreen = currentSelectedTab, modifier = Modifier.fillMaxWidth()){
                selected -> navController.navigate(selected.route){
                    launchSingleTop = true
                    restoreState = true
                
                    popUpTo(navController.graph.findStartDestination().id){ saveState = true}
            } }
        } else {
            Spacer(
                modifier = Modifier
                    .windowInsetsBottomHeight(WindowInsets.navigationBars)
                    .fillMaxWidth()
            )
        }
    }) {
        Row(Modifier.fillMaxWidth()) {
            if(!useBottomNavigation){
                val currentSelectedTab by navController.currentScreenState()
                MainNavigationRail(selectedScreen = currentSelectedTab, modifier = Modifier.fillMaxWidth()){
                        selected -> navController.navigate(selected.route){
                    launchSingleTop = true
                    restoreState = true

                    popUpTo(navController.graph.findStartDestination().id){ saveState = true}
                } }

                Divider(
                    Modifier
                        .fillMaxHeight()
                        .width(1.dp))
            }
            MovieNavigation(Modifier.fillMaxHeight(), navController)
        }
    }
}

@Composable
private fun NavHostController.currentScreenState(): State<Screen>{
    val selected = remember {
        mutableStateOf<Screen>(Screen.Movie)
    }

    DisposableEffect(key1 = this){
        val listener = NavController.OnDestinationChangedListener{ _, destination, _ ->
            selected.value = when{
                destination.hierarchy.any{ it.route == Screen.Movie.route } -> Screen.Movie
                destination.hierarchy.any { it.route == Screen.TV.route } -> Screen.TV
                else -> Screen.Search
            }
        }
        addOnDestinationChangedListener(listener)

        onDispose {
            removeOnDestinationChangedListener(listener)
        }
    }

    return selected
}