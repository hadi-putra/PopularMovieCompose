package com.tbi.moviecompose.data.network.response

import com.google.gson.annotations.SerializedName

data class Author(
    @SerializedName("avatar_path")
    val avatarPath: String,
    val name: String,
    val rating: Any,
    val username: String
)