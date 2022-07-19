package com.tbi.moviecompose.data.network.response

import com.google.gson.annotations.SerializedName

data class ProductionCountry(
    @SerializedName("iso_3166_1")
    val code: String,
    val name: String
)