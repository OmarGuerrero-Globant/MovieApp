package com.example.data.repositories

import com.example.data.remote.services.MoviesServices
import com.example.domain.dto.MovieDto
import com.example.domain.repository.MoviesRepository
import io.reactivex.Single

class MovieDataRepository(private val moviesServices: MoviesServices) : MoviesRepository {

    override fun getMovieById(id: Int): Single<MovieDto> {
        return moviesServices.getMovieById(id.toLong())
            .flatMap {response ->
                return@flatMap if(response.isSuccessful){
                    response.body()?.let {
                        Single.just(MovieDto(it.id, it.title, it.overview, it.posterPath))
                    }
                }else{
                    Single.error(Throwable(response.errorBody().toString()))
                }
            }
    }

    override fun getMovies(page : Int): Single<List<MovieDto>> {
        return moviesServices.getMovies()
            .flatMap {response ->
                return@flatMap if(response.isSuccessful){
                     response.body()?.let { listOfMovies ->
                        Single.just(  listOfMovies.results?.map {movie ->
                            MovieDto(movie.id, movie.title, movie.overview, movie.posterPath)
                        })
                    }
                }else{
                    Single.error(Throwable(response.errorBody().toString()))
                }
            }
    }
}