package com.example.movieapp.main

import com.example.domain.dto.MovieDto

interface PopularContract {

    interface View{

        fun onMoviesLoaded(list : List<MovieDto>)
        fun onMoviesLoadedFailed(message: String)
    }

    interface Presenter{
        fun getMovieList(page : Int)
        fun onDestroy()
    }

}