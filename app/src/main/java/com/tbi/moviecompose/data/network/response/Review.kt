package com.tbi.moviecompose.data.network.response

import com.google.gson.annotations.SerializedName

data class Review(
    val author: String,
    @SerializedName("author_details")
    val authorDetails: Author,
    val content: String,
    @SerializedName("created_at")
    val createdAt: String,
    val id: String,
    @SerializedName("updated_at")
    val updated_at: String,
    val url: String
)