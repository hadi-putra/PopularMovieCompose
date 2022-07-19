package com.tbi.moviecompose.data.datasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.tbi.moviecompose.data.network.Api
import com.tbi.moviecompose.data.network.MovieService
import com.tbi.moviecompose.data.network.response.MovieResponse
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class MoviePagingDataSource @Inject constructor(
    private val service: MovieService
): PagingSource<Int, MovieResponse>() {
    override fun getRefreshKey(state: PagingState<Int, MovieResponse>): Int? {
        return state.anchorPosition/*?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }*/
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieResponse> {
        return try {
            val pageIndex = params.key ?: Api.FIRST_PAGE_INDEX
            val response = service.discover(page = pageIndex)

            LoadResult.Page(
                data = response.results,
                prevKey = if (pageIndex == Api.FIRST_PAGE_INDEX) null else pageIndex-1,
                nextKey = if (response.results.isEmpty()) null else response.page + 1
            )
        } catch (ioe: IOException){
            LoadResult.Error(ioe)
        } catch (he: HttpException) {
            LoadResult.Error(he)
        }
    }
}