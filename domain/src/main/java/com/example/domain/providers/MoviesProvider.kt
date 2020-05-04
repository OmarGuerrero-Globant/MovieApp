package com.example.domain.providers

import com.example.domain.dto.MovieDto
import com.example.domain.usecases.SingleUseCase
import com.example.domain.usecases.movies.GetMovieByIdUseCase
import com.example.domain.usecases.movies.GetMoviesUseCase

interface MoviesProvider {
    fun getMoviesUseCase() : SingleUseCase<GetMoviesUseCase.Params, List<MovieDto>>
    fun getMovieByIdUseCase() : SingleUseCase<GetMovieByIdUseCase.Params, MovieDto>
}