package com.example.data

import com.example.data.remote.services.MoviesServices
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import com.example.data.repositories.MovieDataRepository
import retrofit2.Response
import kotlin.properties.Delegates

@RunWith(MockitoJUnitRunner.StrictStubs::class)
class MovieDataRepositoryTest {

    private var mockPage : Int by Delegates.notNull()
    private lateinit var mockId : String
    private lateinit var  message : String
    private lateinit var movieMock : GetMovieResponse
    private lateinit var moviesMock : List<GetMovieResponse>

    @Mock
    private lateinit var moviesServices: MoviesServices

    private val movieDataRepository : MovieDataRepository by lazy {
        MovieDataRepository(moviesServices)
    }

    @Before
    fun setUp(){
        mockPage = 1
        mockId = "1"
        message = "Not Found"
        movieMock = GetMovieResponse("1", "test", "overview","imageTest")
        moviesMock = listOf(
            GetMovieResponse("1", "test", "overview","imageTest"),
            GetMovieResponse("2", "test", "overview","imageTest")
        )
    }

    @Test
    fun validateGetMovieByIdSuccess(){
        Mockito.`when`(moviesServices.getMovieById(1)).thenReturn(Single.just(Response.success(movieMock)))
        movieDataRepository.getMovieById("1")
            .test()
            .assertComplete()
            .assertNoErrors()
            .assertValue { movieDto ->
                movieDto.id == "1"
            }
    }

    @Test
    fun validateGetMoviesSuccess(){
        Mockito.`when`(moviesServices.getMovies(1)).
        thenReturn(Single.just(Response.success(moviesMock)))

        movieDataRepository.getMovies(mockPage)
            .test()
            .assertComplete()
            .assertNoErrors()
            .assertValue { movies ->
                movies.size == 2
            }
    }

    @Test
    fun validateGetMovieError(){
        Mockito.`when`(moviesServices.getMovies(1)).thenReturn(Single.error(Throwable(message)))
        movieDataRepository.getMovies(mockPage)
            .test()
            .assertError{
                it.message == message
            }
            .assertNotComplete()
    }

    @Test
    fun validateGetMovieByIdError(){
        Mockito.`when`(moviesServices.getMovieById(1)).thenReturn(Single.error(Throwable(message)))
        movieDataRepository.getMovieById(mockId)
            .test()
            .assertError {
                it.message == message
            }
            .assertNotComplete()
    }
}