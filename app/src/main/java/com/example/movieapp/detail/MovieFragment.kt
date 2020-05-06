package com.example.movieapp.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.domain.dto.MovieDto
import com.example.movieapp.R
import com.example.movieapp.common.loadUrl
import kotlinx.android.synthetic.main.fragment_movie_detail.*

class MovieFragment : Fragment(), MovieContract.View {

    private lateinit var presenter: MoviePresenter
    private lateinit var registry: MovieRegistry

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_movie_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //val id = arguments?.getInt("movieId", 545609)
        val id = 545609
        registry = MovieRegistry()
        presenter = registry.provide(this) as MoviePresenter
        id.let { presenter.getMovie(it) }
    }

    override fun onMovieLoaded(movieDto: MovieDto) {
        titleTV.text = movieDto.title
        overviewTV.text = movieDto.overview
        movieDto.frontImage?.let { posterImage.loadUrl(it) }
    }

    override fun onMovieLoadedFailed(message: String) {
        titleTV.text = "Unknown"
        overviewTV.text = "Failed request"
        posterImage.loadUrl("https://image.tmdb.org/t/p/w185/wlfDxbGEsW58vGhFljKkcR5IxDj.jpg")
    }

}