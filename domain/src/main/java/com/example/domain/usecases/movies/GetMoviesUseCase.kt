package com.example.domain.usecases.movies

import com.example.domain.dto.MovieDto
import com.example.domain.executors.PostExecutionThread
import com.example.domain.executors.ThreadExecutor
import com.example.domain.repository.MoviesRepository
import com.example.domain.usecases.SingleUseCase
import io.reactivex.Single

class GetMoviesUseCase(private val moviesRepository: MoviesRepository,
                       threadExecutor: ThreadExecutor,
                       postExecutionThread: PostExecutionThread
) : SingleUseCase<GetMoviesUseCase.Params, List<MovieDto>>(threadExecutor, postExecutionThread) {

    data class Params(val page : Int)

    override fun buildSingleUseCase(params: Params?) : Single<List<MovieDto>> {
        return params?.let {
            moviesRepository.getMovies(params.page)
        } ?: Single.error(Throwable("Invalid Argument"))
    }
}