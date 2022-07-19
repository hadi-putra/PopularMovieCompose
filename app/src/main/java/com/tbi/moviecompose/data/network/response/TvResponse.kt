package com.tbi.moviecompose.data.network.response

import com.google.gson.annotations.SerializedName

data class  ListTvResponse(
    val page: Int,
    val results: List<TvResponse>,
    @SerializedName("total_pages") val totalPages: Int,
    @SerializedName("total_results") val totalResults: Int
)

data class TvResponse(
    val id: Int,
    @SerializedName("backdrop_path") val backdropUri: String,
    @SerializedName("genre_ids") val genreIds: List<Int>,
    @SerializedName("original_language") val language: String,
    @SerializedName("original_name") val originalName: String,
    val overview: String,
    val popularity: Double,
    @SerializedName("name") val title: String,
    @SerializedName("first_air_date") val firstAirDate: String,
    @SerializedName("poster_path") val posterUri: String,
    @SerializedName("vote_average") val voteAverage: Float,
    @SerializedName("vote_count") val voteCount: Int,
    val video: Boolean? = null,
    @SerializedName("origin_country") val originCountry: List<String>? = null
)