package com.tbi.moviecompose.data

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow


interface ShowRepository<E : Any> {

    fun getDiscovery(): Flow<PagingData<E>>
}