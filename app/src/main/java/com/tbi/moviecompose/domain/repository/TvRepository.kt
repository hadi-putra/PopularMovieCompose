package com.tbi.moviecompose.domain.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.tbi.moviecompose.data.ShowRepository
import com.tbi.moviecompose.data.datasource.TvPagingDataSource
import com.tbi.moviecompose.data.network.Api
import com.tbi.moviecompose.data.network.TVService
import com.tbi.moviecompose.data.network.response.TvResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TvRepository @Inject constructor(
    private val service: TVService
) {

    fun getDiscovery(): Flow<PagingData<TvResponse>> {
        return Pager(
            config = PagingConfig(
                pageSize = Api.NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { TvPagingDataSource(service) }
        ).flow
    }
}