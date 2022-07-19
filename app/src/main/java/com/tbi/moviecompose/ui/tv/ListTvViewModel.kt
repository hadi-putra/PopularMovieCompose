package com.tbi.moviecompose.ui.tv

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.tbi.moviecompose.data.ShowRepository
import com.tbi.moviecompose.data.network.response.TvResponse
import com.tbi.moviecompose.di.TvRepo
import com.tbi.moviecompose.domain.repository.TvRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ListTvViewModel @Inject constructor(
    private val repository: TvRepository
): ViewModel() {
    val pageTvList = repository.getDiscovery()
        .cachedIn(viewModelScope)
}