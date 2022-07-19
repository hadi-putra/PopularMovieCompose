package com.tbi.moviecompose.data.network

import com.tbi.moviecompose.data.network.response.*
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieService {

    @GET("discover/movie")
    suspend fun discover(
        @Query("sort_by") sort: String = "popularity.desc",
        @Query("page") page: Int = 1): ListResponse<MovieResponse>

    @GET("movie/{movie_id}")
    suspend fun detail(
        @Path("movie_id") movieId: Int
    ): MovieDetailResponse

    @GET("movie/{movie_id}/videos")
    suspend fun videos(
        @Path("movie_id") movieId: Int
    ): VideoListResponse

    @GET("movie/{movie_id}/reviews")
    suspend fun reviews(
        @Path("movie_id") movieId: Int,
        @Query("page") page: Int = 1
    ): ListResponse<Review>

    @GET("movie/{movie_id}/credits")
    suspend fun casts(
        @Path("movie_id") movieId: Int
    ): ListCastResponse

    @GET("movie/{movie_id}/recommendations")
    suspend fun recommendation(
        @Path("movie_id") movieId: Int,
        @Query("page") page: Int = 1): ListResponse<MovieResponse>

    @GET("movie/{movie_id}/similar")
    suspend fun similar(
        @Path("movie_id") movieId: Int,
        @Query("page") page: Int = 1): ListResponse<MovieResponse>
}