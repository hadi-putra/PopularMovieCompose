package com.tbi.moviecompose.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
import com.tbi.moviecompose.ui.theme.MovieComposeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            //val widthSizeClass = calculateWindowSizeClass(activity = this).widthSizeClass
            MovieComposeTheme {
                // A surface container using the 'background' color from the theme
                MainScreen()
            }
        }
    }
}