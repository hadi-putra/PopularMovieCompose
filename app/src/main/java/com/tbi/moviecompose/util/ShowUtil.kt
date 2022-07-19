package com.tbi.moviecompose.util

import androidx.compose.ui.graphics.Color
import androidx.core.text.buildSpannedString
import com.tbi.moviecompose.data.network.response.MovieResponse
import com.tbi.moviecompose.data.network.response.TvResponse
import java.text.NumberFormat
import java.util.*

val MovieResponse.rateColor: Color
    get() = when{
        voteAverage >= 7 -> Color.Green
        voteAverage >= 4 && voteAverage < 7 -> Color.Yellow
        else -> Color.Red
    }

val Float.rateColor: Color
    get() = when{
        this >= 7 -> Color.Green
        this >= 4 && this < 7 -> Color.Yellow
        else -> Color.Red
    }

val TvResponse.rateColor: Color
    get() = when{
        voteAverage >= 7 -> Color.Green
        voteAverage >= 4 && voteAverage < 7 -> Color.Yellow
        else -> Color.Red
    }

fun Int.toHourDuration(): String{
    if (this < 60) return "$this m"
    val hour = this / 60
    val min = this % 60

    return "$hour h $min m"
}

fun Collection<String>.buildDottedString(): CharSequence? {
    if (isNotEmpty()){
        val spanned = buildSpannedString {
            this@buildDottedString.forEachIndexed{ index, text ->
                append(text)
                if (index < this@buildDottedString.size -1 )
                    append(" \u2022 ")
            }
        }
        return spanned
    }
    return null
}

fun Long.getDollarCurrency(): String{
    val numberFormat = NumberFormat.getCurrencyInstance().apply {
        maximumFractionDigits = 2
        currency = Currency.getInstance(Locale.US)
    }
    return numberFormat.format(this)
}