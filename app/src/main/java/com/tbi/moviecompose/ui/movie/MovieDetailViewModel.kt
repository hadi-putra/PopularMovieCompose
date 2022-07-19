package com.tbi.moviecompose.ui.movie

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tbi.moviecompose.data.Result
import com.tbi.moviecompose.data.network.response.*
import com.tbi.moviecompose.domain.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: MovieRepository,
): ViewModel() {

    val movieDetail = mutableStateOf(MovieDetailState(refresh = true))
    val videos = mutableStateOf<List<Video>>(listOf())
    val reviews = mutableStateOf<List<Review>>(listOf())
    val casts = mutableStateOf<List<Cast>>(listOf())
    val similarMovies = mutableStateOf<List<MovieResponse>>(listOf())
    val recommendationMovies = mutableStateOf<List<MovieResponse>>(listOf())

    init {
        Log.i("MOVIE COMPOSE", "${javaClass.canonicalName} is injected")
        val id = savedStateHandle.get<Int>("showId")?.let { id ->
            getDetail(id)
            getVideos(id)
            getReviews(id)
            getCasts(id)
            getSimilarMovies(id)
            getRecommendationMovies(id)
        }
    }

    private fun getDetail(id: Int){
        viewModelScope.launch {
            val result = repository.getDetail(id)
            movieDetail.value = when(result){
                is Result.Success -> MovieDetailState(detail = result.result, refresh = false)
                is Result.Fail -> movieDetail.value.copy(error = result.error, refresh = false)
            }
        }
    }

    private fun getVideos(id: Int){
        viewModelScope.launch {
            val result = repository.getVideos(id)
            videos.value = when(result){
                is Result.Success -> result.result
                else -> listOf()
            }
        }
    }

    private fun getReviews(id: Int){
        viewModelScope.launch {
            val result = repository.getReviews(id)
            reviews.value = when(result){
                is Result.Success -> result.result
                else -> listOf()
            }
        }
    }

    private fun getCasts(id: Int){
        viewModelScope.launch {
            val result = repository.getCasts(id)
            casts.value = when(result){
                is Result.Success -> result.result
                else -> listOf()
            }
        }
    }

    private fun getSimilarMovies(id: Int){
        viewModelScope.launch {
            val result = repository.getSimilarMovie(id)
            similarMovies.value = when(result){
                is Result.Success -> result.result
                else -> listOf()
            }
        }
    }

    private fun getRecommendationMovies(id: Int){
        viewModelScope.launch {
            val result = repository.getRecommendation(id)
            recommendationMovies.value = when(result){
                is Result.Success -> result.result
                else -> listOf()
            }
        }
    }
}