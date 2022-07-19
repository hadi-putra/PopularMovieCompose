package com.tbi.moviecompose.data.network.response

import com.google.gson.annotations.SerializedName

data class MovieResponse(
    val id: Int,
    @SerializedName("backdrop_path") val backdropUri: String,
    @SerializedName("genre_ids") val genreIds: List<Int>,
    @SerializedName("original_language") val language: String,
    @SerializedName("original_title") val originalTitle: String,
    val overview: String,
    val popularity: Double,
    @SerializedName("poster_path") val posterUri: String,
    @SerializedName("title") val title: String,
    @SerializedName("release_date") val releaseDate: String,
    @SerializedName("vote_average") val voteAverage: Float,
    @SerializedName("vote_count") val voteCount: Int,
    @SerializedName("adult") val isAdult: Boolean? = null,
    val video: Boolean? = null
)