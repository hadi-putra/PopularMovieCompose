package com.tbi.moviecompose.domain.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.tbi.moviecompose.data.Result
import com.tbi.moviecompose.data.ShowRepository
import com.tbi.moviecompose.data.datasource.MoviePagingDataSource
import com.tbi.moviecompose.data.network.Api
import com.tbi.moviecompose.data.network.MovieService
import com.tbi.moviecompose.data.network.response.*
import com.tbi.moviecompose.util.DispatchersProvider
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import java.io.IOException
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val service: MovieService,
    private val dispatchers: DispatchersProvider
) {

    fun getDiscovery(): Flow<PagingData<MovieResponse>> {
        return Pager(
            config = PagingConfig(
                pageSize = Api.NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { MoviePagingDataSource(service) }
        ).flow
    }

    suspend fun getDetail(id: Int): Result<MovieDetailResponse> = withContext(dispatchers.io){
        try {
            Result.Success(service.detail(id))
        } catch (ioe: IOException){
            Result.Fail(ioe)
        } catch (e: Exception){
            Result.Fail(e)
        }
    }

    suspend fun getVideos(id: Int): Result<List<Video>> = withContext(dispatchers.io){
        try {
            Result.Success(service.videos(id).results)
        } catch (ioe: IOException){
            Result.Fail(ioe)
        } catch (e: Exception){
            Result.Fail(e)
        }
    }

    suspend fun getReviews(id: Int): Result<List<Review>> = withContext(dispatchers.io){
        try {
            Result.Success(service.reviews(id).results)
        } catch (ioe: IOException){
            Result.Fail(ioe)
        } catch (e: Exception){
            Result.Fail(e)
        }
    }

    suspend fun getCasts(id: Int): Result<List<Cast>> = withContext(dispatchers.io){
        try {
            Result.Success(service.casts(id).cast)
        } catch (ioe: IOException){
            Result.Fail(ioe)
        } catch (e: Exception){
            Result.Fail(e)
        }
    }

    suspend fun getSimilarMovie(id: Int): Result<List<MovieResponse>> = withContext(dispatchers.io){
        try {
            Result.Success(service.similar(id).results)
        } catch (ioe: IOException){
            Result.Fail(ioe)
        } catch (e: Exception){
            Result.Fail(e)
        }
    }

    suspend fun getRecommendation(id: Int): Result<List<MovieResponse>> = withContext(dispatchers.io){
        try {
            Result.Success(service.recommendation(id).results)
        } catch (ioe: IOException){
            Result.Fail(ioe)
        } catch (e: Exception){
            Result.Fail(e)
        }
    }
}