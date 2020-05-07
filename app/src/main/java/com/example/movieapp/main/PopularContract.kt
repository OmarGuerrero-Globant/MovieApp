package com.example.movieapp.main

import com.example.domain.dto.MovieDto

interface PopularContract {

    interface View{

        fun onMoviesLoaded(listOfMovies : List<MovieDto>)
        fun onMoviesLoadedFailed(errorMessage: String)
    }

    interface Presenter{
        fun getMovieList(page : Int)
        fun onDestroy()
    }

}