package com.tbi.moviecompose.ui.movie

import androidx.compose.runtime.Immutable
import com.tbi.moviecompose.data.network.response.MovieDetailResponse

@Immutable
data class MovieDetailState (
    val detail: MovieDetailResponse? = null,
    val refresh: Boolean = false,
    val error: Throwable? = null
)
