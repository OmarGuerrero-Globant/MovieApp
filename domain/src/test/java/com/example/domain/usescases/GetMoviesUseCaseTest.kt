package com.example.domain.usescases

import com.example.domain.dto.MovieDto
import com.example.domain.executors.PostExecutionThread
import com.example.domain.repository.MoviesRepository
import com.example.domain.usecases.movies.GetMoviesUseCase
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner.StrictStubs::class)
class GetMoviesUseCaseTest{

    @Mock
    private lateinit var moviesRepository: MoviesRepository
    @Mock
    private lateinit var postExecutionThread: PostExecutionThread

    private val getMoviesUseCase : GetMoviesUseCase by lazy {
        GetMoviesUseCase(moviesRepository, ImmediateThreadExecutor(), postExecutionThread)
    }

    @Before
    fun setUp(){
        Mockito.`when`(postExecutionThread.getScheduler()).thenReturn(Schedulers.trampoline())
    }

    @Test
    fun validateGetMoviesSuccess(){
        val params = GetMoviesUseCase.Params(1)
        val listOfMoviesMock = listOf<MovieDto>(
            MovieDto("1", "title", "overview", "image"),
            MovieDto("2", "title", "overview", "image")
        )
        Mockito.`when`(moviesRepository.getMovies(1)).thenReturn(Single.just(listOfMoviesMock))
        getMoviesUseCase.execute(params)
            .test()
            .assertComplete()
            .assertNoErrors()
            .assertValue{ listOfMovies ->
                listOfMovies.size == 2
            }
    }

    @Test
    fun validateGetMovieError(){
        val params = GetMoviesUseCase.Params(1)
        val listOfMoviesMock = listOf<MovieDto>(
            MovieDto("1", "title", "overview", "image"),
            MovieDto("2", "title", "overview", "image")
        )
        val message : String = "Not Found"
        Mockito.`when`(moviesRepository.getMovies(1)).thenReturn(Single.error(Throwable(message)))
        getMoviesUseCase.execute(params)
            .test()
            .assertError{
                it.message == message
            }
            .assertNotComplete()
    }

    @Test
    fun validateGetMovieFailed(){
        val params = GetMoviesUseCase.Params(1)
        val listOfMoviesMock = listOf<MovieDto>(
            MovieDto("1", "title", "overview", "image"),
            MovieDto("2", "title", "overview", "image")
        )
        val message : String = "Invalid Argument"
        getMoviesUseCase.execute(null)
            .test()
            .assertError{
                it.message == message
            }
            .assertNotComplete()
    }


}