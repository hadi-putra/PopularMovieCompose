package com.tbi.moviecompose.data.network.response

import com.google.gson.annotations.SerializedName

data class Video(
    val id: String,
    val key: String,
    val name: String,
    val official: Boolean,
    @SerializedName("published_at")
    val publishedAt: String,
    val site: String,
    val size: Int,
    val type: String
)

data class VideoListResponse(
    val results: List<Video>
)