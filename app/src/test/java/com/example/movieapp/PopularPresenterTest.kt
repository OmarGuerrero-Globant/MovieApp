package com.example.movieapp

import com.example.domain.dto.MovieDto
import com.example.domain.executors.PostExecutionThread
import com.example.domain.providers.MoviesProvider
import com.example.domain.repository.MoviesRepository
import com.example.domain.usecases.movies.GetMoviesUseCase
import com.example.movieapp.main.PopularContract
import com.example.movieapp.main.PopularPresenter
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
class PopularPresenterTest {

    private var page : Int by Delegates.notNull()

    private val popularPresenter : PopularPresenter by lazy {
        PopularPresenter(moviesProvider, view)
    }

    @Mock
    private lateinit var moviesProvider : MoviesProvider
    @Mock
    private lateinit var view: PopularContract.View
    @Mock
    private lateinit var moviesRepository: MoviesRepository
    @Mock
    private lateinit var postExecutionThread : PostExecutionThread

    @Before
    fun setUp(){
        page = 1
        Mockito.`when`(postExecutionThread.getScheduler()).thenReturn(
            Schedulers.trampoline()
        )
    }

    @Test
    fun validateGetMovieListSuccess(){

        //Paso 1

        val moviesListMock =  listOf<MovieDto>(
            MovieDto(1, "title", "overview", "image"),
            MovieDto(2, "title2", "overview2", "image2")
        )

        //Paso 2

        Mockito.`when`(moviesRepository.getMovies(page)).thenReturn(
            Single.just(moviesListMock)
        )

        Mockito.`when`(moviesProvider.getMoviesUseCase()).thenReturn(
            GetMoviesUseCase(moviesRepository, ImmediateThreadExecutor() , postExecutionThread)
        )

        //Paso 3
        popularPresenter.getMovieList(1)

        //Paso 4
        Mockito.verify(view).onMoviesLoaded(moviesListMock)
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
        popularPresenter.getMovieList(1)

        //Paso 4
        Mockito.verify(view).onMoviesLoadedFailed(messageError)
    }
}