package com.example.movieapp.detail

import com.example.domain.dto.MovieDto

interface MovieContract {

    interface View{
        fun onMovieLoaded(movieDto: MovieDto)
        fun onMovieLoadedFailed(message : String)
    }

    interface Presenter{
        fun getMovie(id: Int)
        fun onDestroy()
    }

}