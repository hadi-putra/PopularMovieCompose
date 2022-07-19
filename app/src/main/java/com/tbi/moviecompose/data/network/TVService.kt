package com.tbi.moviecompose.data.network

import com.tbi.moviecompose.data.network.response.ListTvResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface TVService {

    @GET("discover/tv")
    suspend fun discover(
        @Query("sort_by") sort: String = "popularity.desc",
        @Query("page") page: Int = 1): ListTvResponse
}