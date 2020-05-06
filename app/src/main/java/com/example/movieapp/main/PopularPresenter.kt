package com.example.movieapp.main

import android.util.Log
import com.example.domain.providers.MoviesProvider
import com.example.domain.usecases.movies.GetMovieByIdUseCase
import com.example.domain.usecases.movies.GetMoviesUseCase
import io.reactivex.disposables.CompositeDisposable

class PopularPresenter(private val moviesProvider: MoviesProvider,
                       private val view : PopularContract.View
)  : PopularContract.Presenter {

    private val disposable = CompositeDisposable()

    override fun getMovieList(page : Int) {
        Log.d("XXX", "Refresh")
        val params = GetMoviesUseCase.Params(page)
        disposable.add(moviesProvider.getMoviesUseCase().execute(params)
            .subscribe({success ->
                view.onMoviesLoaded(success)
            }, {error ->
                view.onMoviesLoadedFailed(error.message ?: "Movies error")
        })
        )
    }

    override fun onDestroy() {
        disposable.dispose()
    }
}