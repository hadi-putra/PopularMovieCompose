package com.tbi.moviecompose.data.network.response

import com.google.gson.annotations.SerializedName

data class Language(
    @SerializedName("english_name")
    val englishName: String,
    @SerializedName("iso_639_1")
    val code: String,
    val name: String
)