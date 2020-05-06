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

class PopularMoviesFragment : Fragment(), PopularContract.View {

    private lateinit var adapter: PopularAdapter
    private lateinit var listOfMovies : List<MovieDto>
    private lateinit var presenter : PopularPresenter
    private lateinit var registry: PopularRegistry
    private lateinit var navController : NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(
        R.layout.fragment_popular_movies,
        container, false
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registry = PopularRegistry()
        presenter = registry.provide(this) as PopularPresenter
        presenter.getMovieList(1)
        progress.visibility = View.VISIBLE
        navController = view.findNavController()
        swipeRefresh.setOnRefreshListener { refresh() }
    }

    override fun onMoviesLoaded(list: List<MovieDto>) {
        progress.visibility = View.GONE
    }

    override fun onMoviesLoadedFailed(message: String) {
        listOfMovies = listOf(
            MovieDto(1, "Test1", "Overview failed", "failed"),
            MovieDto(2, "Test2", "", "failed"),
            MovieDto(3, "Test3", "", "failed")
        )
        adapter = PopularAdapter(listOfMovies){ navigateToDetail(it)}
        recycler.adapter = adapter
        progress.visibility = View.GONE
        swipeRefresh.isRefreshing = false
    }


    private fun navigateToDetail(movieDto: MovieDto){
       navController.navigate(R.id.action_popularMoviesFragment_to_movieFragment,
       bundleOf("nothing" to movieDto.id))
    }

    private fun refresh(){
        presenter.getMovieList(1)
    }

}