package com.example.movieapp

import com.example.domain.dto.MovieDto
import com.example.domain.executors.PostExecutionThread
import com.example.domain.providers.MoviesProvider
import com.example.domain.repository.MoviesRepository
import com.example.domain.usecases.movies.GetMovieByIdUseCase
import com.example.domain.usecases.movies.GetMoviesUseCase
import com.example.movieapp.main.MainContract
import com.example.movieapp.main.MainPresenter
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import kotlin.properties.Delegates

@RunWith(MockitoJUnitRunner.StrictStubs::class)
class MainPresenterTest {

    private lateinit var id  : String
    private var page : Int by Delegates.notNull()

    private val mainPresenter : MainPresenter by lazy {
        MainPresenter(moviesProvider, view)
    }

    @Mock
    private lateinit var moviesProvider : MoviesProvider
    @Mock
    private lateinit var view: MainContract.View
    @Mock
    private lateinit var moviesRepository: MoviesRepository
    @Mock
    private lateinit var postExecutionThread : PostExecutionThread

    @Before
    fun setUp(){
        id = "1"
        page = 1
        Mockito.`when`(postExecutionThread.getScheduler()).thenReturn(
            Schedulers.trampoline()
        )
    }

    @Test
    fun validateGetMovieSuccess(){
        /*
        * Paso 1 modelos con los que vamos a contestar
        * Paso 2 stubs (Cuando que sucede - hacer que)
        * Paso 3 lanzar la prueba
        * Paso 4 validar llamadas, callbacks, returns
        * */

        //Paso 1
        val movieMock = MovieDto(id, "title", "overview", "image")

        //Paso 2
        Mockito.`when`(moviesRepository.getMovieById(id)).thenReturn(
            Single.just(movieMock)
        )

        //Paso 2
        Mockito.`when`(moviesProvider.getMovieByIdUseCase()).thenReturn(
            GetMovieByIdUseCase(moviesRepository, ImmediateThreadExecutor(), postExecutionThread )
        )

        //Paso 3
        mainPresenter.getMovie(id)

        //Paso 4
        Mockito.verify(view).onMovieLoaded(movieMock)
    }

    @Test
    fun validateGetMovieError(){

        //Paso 1
        val messageError = "Not Found"

        //Paso 2
        Mockito.`when`(moviesRepository.getMovieById(id)).thenReturn(
            Single.error(Throwable(messageError))
        )

        Mockito.`when`(moviesProvider.getMovieByIdUseCase()).thenReturn(
            GetMovieByIdUseCase(moviesRepository, ImmediateThreadExecutor() , postExecutionThread)
        )

        //Paso 3
        mainPresenter.getMovie(id)

        //Paso 4
        Mockito.verify(view).onMovieLoadedFailed(messageError)
    }

    @Test
    fun validateGetMovieListSuccess(){

        //Paso 1

        val moviesListMock =  listOf<MovieDto>(
            MovieDto("1", "title", "overview", "image"),
            MovieDto("2", "title2", "overview2", "image2")
        )

        //Paso 2

        Mockito.`when`(moviesRepository.getMovies(page)).thenReturn(
            Single.just(moviesListMock)
        )

        Mockito.`when`(moviesProvider.getMoviesUseCase()).thenReturn(
            GetMoviesUseCase(moviesRepository, ImmediateThreadExecutor() , postExecutionThread)
        )

        //Paso 3
        mainPresenter.getMovieList(1)

        //Paso 4
        Mockito.verify(view).onMoviesLoaded(Mockito.anyListOf(MovieDto::class.java))
    }

    @Test
    fun validateGetMovieListError(){

        //Paso 1
        val messageError = "Not Found"

        //Paso 2

        Mockito.`when`(moviesRepository.getMovies(page)).thenReturn(
            Single.error(Throwable(messageError))
        )

        Mockito.`when`(moviesProvider.getMoviesUseCase()).thenReturn(
            GetMoviesUseCase(moviesRepository, ImmediateThreadExecutor(), postExecutionThread)
        )

        //Paso 3
        mainPresenter.getMovieList(1)

        //Paso 4
        Mockito.verify(view).onMoviesLoadedFailed(messageError)
    }
}