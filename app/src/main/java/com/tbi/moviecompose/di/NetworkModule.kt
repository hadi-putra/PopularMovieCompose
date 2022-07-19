package com.tbi.moviecompose.di

import com.tbi.moviecompose.BuildConfig
import com.tbi.moviecompose.data.network.Api
import com.tbi.moviecompose.data.network.MovieService
import com.tbi.moviecompose.data.network.TVService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val builder = OkHttpClient.Builder()

        val addKeyInterceptor = Interceptor{
            var request = it.request()
            val httpUrl = request.url.newBuilder()
                .addQueryParameter("api_key", BuildConfig.THE_MOVIEDB_KEY).build()
            request = request.newBuilder().url(httpUrl).build()
            it.proceed(request)
        }

        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level =HttpLoggingInterceptor.Level.BODY
        }
        builder.addInterceptor(loggingInterceptor)
        if (!builder.interceptors().contains(addKeyInterceptor))
            builder.addInterceptor(addKeyInterceptor)

        return builder.build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient) = Retrofit.Builder()
        .baseUrl(Api.BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    @Singleton
    fun provideMovieService(retrofit: Retrofit) = retrofit.create(MovieService::class.java)

    @Provides
    @Singleton
    fun provideTvService(retrofit: Retrofit) = retrofit.create(TVService::class.java)
}