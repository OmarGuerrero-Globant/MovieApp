package com.example.movieapp.common

import com.example.data.executors.JobExecutor
import com.example.data.executors.UIThread
import com.example.data.providers.MoviesDataProvider
import com.example.data.remote.services.MoviesServices
import com.example.data.repositories.MovieDataRepository
import com.example.domain.executors.PostExecutionThread
import com.example.domain.executors.ThreadExecutor
import com.example.domain.providers.MoviesProvider
import com.example.domain.repository.MoviesRepository

open class BaseRegistry {

    private val threadExecutor : ThreadExecutor by lazy{
        JobExecutor()
    }

    private val postExecutionThread : PostExecutionThread by lazy {
        UIThread()
    }

    private fun getMoviesServices() : MoviesServices{
        return RetrofitFactory.buildRetrofitService().create(MoviesServices::class.java)
    }

    private val moviesRepository : MoviesRepository by lazy {
        MovieDataRepository(getMoviesServices())
    }

    val moviesProvider : MoviesProvider by lazy {
        MoviesDataProvider(moviesRepository, threadExecutor, postExecutionThread)
    }

}