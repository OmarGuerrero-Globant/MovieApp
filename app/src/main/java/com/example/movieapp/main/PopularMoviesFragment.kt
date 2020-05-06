package com.example.movieapp.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.example.domain.dto.MovieDto
import com.example.movieapp.R
import kotlinx.android.synthetic.main.fragment_popular_movies.*

class PopularMoviesFragment : Fragment(), MainContract.View {

    private lateinit var adapter: MainAdapter
    private lateinit var listOfMovies : List<MovieDto>
    private lateinit var presenter : MainPresenter
    private lateinit var registry: MainRegistry
    private lateinit var navController : NavController

    companion object{
        const val EXTRA_TEXT: String = "text"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(
        R.layout.fragment_popular_movies,
        container, false
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registry = MainRegistry()
        presenter = registry.provide(this) as MainPresenter
        presenter.getMovieList(1)
        progress.visibility = View.VISIBLE
        navController = view.findNavController()
    }


    override fun onMovieLoaded(movieDto: MovieDto) {

    }

    override fun onMovieLoadedFailed(message: String) {

    }

    override fun onMoviesLoaded(list: List<MovieDto>) {
        progress.visibility = View.GONE
        //Toast.makeText(this, "Success request", Toast.LENGTH_SHORT).show()
    }

    override fun onMoviesLoadedFailed(message: String) {
        listOfMovies = listOf(
            MovieDto(1, "Test1", "Overview failed", "failed"),
            MovieDto(2, "Test2", "", "failed"),
            MovieDto(3, "Test3", "", "failed")
        )
        adapter = MainAdapter(listOfMovies){ navigateToDetail(it)}
        recycler.adapter = adapter
        progress.visibility = View.GONE
        //Toast.makeText(this, "Failed request", Toast.LENGTH_SHORT).show()
    }


    private fun navigateToDetail(movieDto: MovieDto){
       navController.navigate(R.id.action_popularMoviesFragment_to_movieFragment,
       bundleOf("movieTitle" to movieDto.title, "overview" to movieDto.overview))
    }

}