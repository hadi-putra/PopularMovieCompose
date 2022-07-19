package com.tbi.moviecompose.data.network.response

import com.google.gson.annotations.SerializedName

data class ListResponse<E>(
    val page: Int,
    val results: List<E>,
    @SerializedName("total_pages") val totalPages: Int,
    @SerializedName("total_results") val totalResults: Int
)
