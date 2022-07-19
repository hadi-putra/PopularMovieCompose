package com.tbi.moviecompose.di

import com.tbi.moviecompose.data.ShowRepository
import com.tbi.moviecompose.data.network.response.MovieResponse
import com.tbi.moviecompose.data.network.response.TvResponse
import com.tbi.moviecompose.domain.repository.MovieRepository
import com.tbi.moviecompose.domain.repository.TvRepository
import com.tbi.moviecompose.util.DefaultDispatcherProvider
import com.tbi.moviecompose.util.DispatchersProvider
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {

    /*@Binds
    @MovieRepo
    abstract fun bindMovieRepository(
        movieRepository: MovieRepository
    ): ShowRepository<MovieResponse>

    @Binds
    @TvRepo
    abstract fun bindTvRepository(
        tvRepository: TvRepository
    ): ShowRepository<TvResponse>*/

    @Binds
    abstract fun bindDispatchersProvider(
        defaultDispatchersProvider: DefaultDispatcherProvider
    ): DispatchersProvider
}