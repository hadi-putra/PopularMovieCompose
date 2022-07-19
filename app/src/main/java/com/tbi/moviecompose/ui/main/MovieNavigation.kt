package com.tbi.moviecompose.ui.main

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.tbi.moviecompose.ui.movie.MovieDetail
import com.tbi.moviecompose.ui.movie.MovieList
import com.tbi.moviecompose.ui.tv.SeriesList

@Composable
internal fun MovieNavigation(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
){
    NavHost(navController = navController,
        startDestination = Screen.Movie.route, modifier = modifier){
        addMovieRoute(navController)
        addTVRoute(navController)
        addSearchRoute(navController)
    }
}

sealed class Screen(val route: String){
    object Movie: Screen("movie")
    object TV: Screen("tv")
    object Search: Screen("search")
}

private sealed class ChildScreen(val route: String){
    fun createRoute(root: Screen) = "${root.route}/$route"

    object Movie: ChildScreen("movie")
    object TV: ChildScreen("tv")
    object Search: ChildScreen("search")

    object Detail: ChildScreen("detail/{showType}/{showId}"){
        fun createRoute(root: Screen, showId: Int, isMovie: Boolean = true): String {
            return "${root.route}/detail/${if (isMovie) "movie" else "tv"}/$showId"
        }
    }
}

private fun NavGraphBuilder.addMovieRoute(navController: NavHostController){
    navigation(
        startDestination = ChildScreen.Movie.createRoute(Screen.Movie),
        route = Screen.Movie.route
        ){
        Log.i("Navigation", "Build Movie Compose")
        composable(route = ChildScreen.Movie.createRoute(Screen.Movie)){
            // add Movie Screen
            MovieList{ movieId ->
                navController.navigate(ChildScreen.Detail.createRoute(Screen.Movie, movieId))
            }
        }
        addDetail(Screen.Movie, navController)
    }
}

private fun NavGraphBuilder.addTVRoute(navController: NavHostController){
    navigation(
        startDestination = ChildScreen.TV.createRoute(Screen.TV),
        route = Screen.TV.route
    ){
        composable(route = ChildScreen.TV.createRoute(Screen.TV)){
            // add TV Screen
            SeriesList { tvId ->
                navController.navigate(ChildScreen.Detail.createRoute(Screen.TV, tvId))
            }
        }
        addDetail(Screen.TV, navController)
    }
}

private fun NavGraphBuilder.addSearchRoute(navController: NavHostController){
    navigation(
        startDestination = ChildScreen.Search.createRoute(Screen.Search),
        route = Screen.Search.route
    ){
        composable(route = ChildScreen.Search.createRoute(Screen.Search)){
            // add TV Screen
        }
        addDetail(Screen.Search, navController)
    }
}

private fun NavGraphBuilder.addDetail(root: Screen, navController: NavHostController){
    composable(
        route = ChildScreen.Detail.createRoute(root),
        arguments = listOf(
            navArgument(name = "showType"){type = NavType.StringType},
            navArgument(name = "showId"){type = NavType.IntType}
        )
    ){
        //add Detail
        val showId = it.arguments?.getInt("showId") ?: return@composable

        if (root == Screen.Movie){
            // openMovieDetail
            MovieDetail(openMovieDetail = { movieId ->
                navController.navigate(ChildScreen.Detail.createRoute(Screen.Movie, movieId))
            }){
                navController.navigateUp()
            }
        } else {
            // openTvDetail
        }
    }
}