package com.tbi.moviecompose.di

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class MovieRepo

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class TvRepo