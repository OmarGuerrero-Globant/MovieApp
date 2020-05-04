package com.example.data.providers

import com.example.domain.dto.MovieDto
import com.example.domain.executors.PostExecutionThread
import com.example.domain.executors.ThreadExecutor
import com.example.domain.providers.MoviesProvider
import com.example.domain.repository.MoviesRepository
import com.example.domain.usecases.SingleUseCase
import com.example.domain.usecases.movies.GetMovieByIdUseCase
import com.example.domain.usecases.movies.GetMoviesUseCase

class MoviesDataProvider(
    private val moviesRepository: MoviesRepository,
    private val threadExecutor: ThreadExecutor,
    private val postExecutionThread: PostExecutionThread
) : MoviesProvider {
    override fun getMoviesUseCase(): SingleUseCase<GetMoviesUseCase.Params, List<MovieDto>> {
        return GetMoviesUseCase(moviesRepository, threadExecutor, postExecutionThread)
    }

    override fun getMovieByIdUseCase(): SingleUseCase<GetMovieByIdUseCase.Params, MovieDto> {
        return GetMovieByIdUseCase(moviesRepository, threadExecutor, postExecutionThread)
    }
}