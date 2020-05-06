package com.example.movieapp.main

import com.example.data.executors.JobExecutor
import com.example.data.executors.UIThread
import com.example.data.providers.MoviesDataProvider
import com.example.data.remote.services.MoviesServices
import com.example.data.repositories.MovieDataRepository
import com.example.domain.executors.PostExecutionThread
import com.example.domain.executors.ThreadExecutor
import com.example.domain.providers.MoviesProvider
import com.example.domain.repository.MoviesRepository
import com.example.movieapp.utils.AuthInterceptor
import com.example.movieapp.utils.ContentTypeInterceptor
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class MainRegistry {

    companion object{
        const val API_URL = "https://developers.themoviedb.org/3/"
    }

    private val threadExecutor : ThreadExecutor by lazy{
        JobExecutor()
    }

    private val postExecutionThread : PostExecutionThread by lazy {
        UIThread()
    }

    private fun getMoviesServices() : MoviesServices{
        val gson = GsonBuilder()
            .setLenient()
            .create()

        val authClient =  OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor())
            .build()

        val contentTypeInterceptor = OkHttpClient.Builder()
            .addInterceptor(ContentTypeInterceptor())
            .build()

        return Retrofit.Builder()
            .baseUrl(API_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(contentTypeInterceptor)
            .client(authClient)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .run {
                create(MoviesServices::class.java)
            }
    }

    private val moviesRepository : MoviesRepository by lazy {
        MovieDataRepository(getMoviesServices())
    }

    private val moviesProvider : MoviesProvider by lazy {
        MoviesDataProvider(moviesRepository, threadExecutor, postExecutionThread)
    }

    fun provide(view : MainContract.View) : MainContract.Presenter {
        return MainPresenter(moviesProvider, view)
    }


}