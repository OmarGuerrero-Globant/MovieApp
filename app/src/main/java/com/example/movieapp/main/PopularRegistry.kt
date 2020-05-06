package com.example.movieapp.main

import com.example.movieapp.common.BaseRegistry

class PopularRegistry : BaseRegistry(){

    fun provide(view : PopularContract.View) : PopularContract.Presenter {
        return PopularPresenter(moviesProvider, view)
    }
}