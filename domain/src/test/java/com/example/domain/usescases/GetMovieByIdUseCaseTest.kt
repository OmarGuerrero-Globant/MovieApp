package com.example.domain.usescases

import com.example.domain.dto.MovieDto
import com.example.domain.executors.PostExecutionThread
import com.example.domain.repository.MoviesRepository
import com.example.domain.usecases.movies.GetMovieByIdUseCase
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner.StrictStubs::class)
class GetMovieByIdUseCaseTest {

    @Mock
    private lateinit var moviesRepository: MoviesRepository
    @Mock
    private lateinit var postExecutionThread: PostExecutionThread

    private val getMovieByIdUseCase : GetMovieByIdUseCase by lazy {
        GetMovieByIdUseCase(moviesRepository, ImmediateThreadExecutor(), postExecutionThread)
    }

    @Before
    fun setUp(){
        Mockito.`when`(postExecutionThread.getScheduler()).thenReturn(Schedulers.trampoline())
    }

    @Test
    fun validateGetMovieSuccess(){
        val params = GetMovieByIdUseCase.Params("1")
        val movieMock = MovieDto("1", "test", "overview","imageTest")
        Mockito.`when`(moviesRepository.getMovieById("1")).thenReturn(Single.just(movieMock))
        getMovieByIdUseCase.execute(params)
            .test()
            .assertComplete()
            .assertNoErrors()
            .assertValue{ movieDto ->
            movieDto.id == "1"
        }
    }

    @Test
    fun validateGetMovieError(){
        val params = GetMovieByIdUseCase.Params("1")
        val movieMock = MovieDto("1", "test", "overview","imageTest")
        val message : String = "Not Found"
        Mockito.`when`(moviesRepository.getMovieById("1")).thenReturn(Single.error(Throwable(message)))
        getMovieByIdUseCase.execute(params)
            .test()
            .assertError{
                it.message == message
            }
            .assertNotComplete()
    }

    @Test
    fun validateGetMovieFailed(){
        val params = GetMovieByIdUseCase.Params("1")
        val movieMock = MovieDto("1", "test", "overview","imageTest")
        val message : String = "Invalid Argument"
        getMovieByIdUseCase.execute(null)
            .test()
            .assertError{
                it.message == message
            }
            .assertNotComplete()
    }

}