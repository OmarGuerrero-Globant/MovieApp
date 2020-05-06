package com.example.movieapp.detail

import com.example.domain.providers.MoviesProvider
import com.example.domain.usecases.movies.GetMovieByIdUseCase
import io.reactivex.disposables.CompositeDisposable

class MoviePresenter(private val moviesProvider: MoviesProvider,
                     private val view : MovieContract.View) : MovieContract.Presenter {

    private val disposable = CompositeDisposable()

    override fun getMovie(id: Int) {
        val params = GetMovieByIdUseCase.Params(id)
        disposable.add(moviesProvider.getMovieByIdUseCase().execute(params)
            .subscribe({ success ->
                view.onMovieLoaded(success)
            }, {error ->
                view.onMovieLoadedFailed(error.message ?: "Movie Error")
            })
        )
    }

    override fun onDestroy() {
        disposable.dispose()
    }

}