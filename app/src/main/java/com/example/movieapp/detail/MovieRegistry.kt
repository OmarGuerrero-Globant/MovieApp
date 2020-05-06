package com.example.movieapp.detail

import com.example.movieapp.common.BaseRegistry

class MovieRegistry : BaseRegistry() {

    fun provide(view : MovieContract.View) : MovieContract.Presenter{
        return MoviePresenter(moviesProvider, view)
    }
}