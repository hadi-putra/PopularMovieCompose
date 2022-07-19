package com.tbi.moviecompose.data

sealed class Result<out T>{
    data class Success<T>(val result: T): Result<T>()
    data class Fail(val error: Throwable): Result<Nothing>()
}