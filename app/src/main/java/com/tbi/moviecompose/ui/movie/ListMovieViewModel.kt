package com.tbi.moviecompose.ui.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.tbi.moviecompose.data.ShowRepository
import com.tbi.moviecompose.data.network.response.MovieResponse
import com.tbi.moviecompose.di.MovieRepo
import com.tbi.moviecompose.domain.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ListMovieViewModel @Inject constructor(
    private val repository: MovieRepository
): ViewModel() {

    val pageMovieList = repository.getDiscovery()
        .cachedIn(viewModelScope)

}