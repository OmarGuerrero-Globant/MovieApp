package com.example.domain.usecases.movies

import com.example.domain.dto.MovieDto
import com.example.domain.executors.PostExecutionThread
import com.example.domain.executors.ThreadExecutor
import com.example.domain.repository.MoviesRepository
import com.example.domain.usecases.SingleUseCase
import io.reactivex.Single

class GetMovieByIdUseCase(private val moviesRepository: MoviesRepository,
                          threadExecutor: ThreadExecutor,
                          postExecutionThread: PostExecutionThread
) : SingleUseCase<GetMovieByIdUseCase.Params, MovieDto>(threadExecutor, postExecutionThread) {

    data class Params(val id : String)

    override fun buildSingleUseCase(params: Params?): Single<MovieDto> {
        return params?.let{
            moviesRepository.getMovieById(params.id)
        } ?: Single.error(Throwable("Invalid Argument"))
    }

}