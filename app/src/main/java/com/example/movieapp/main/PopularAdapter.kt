package com.example.movieapp.main

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.dto.MovieDto
import com.example.movieapp.R
import com.example.movieapp.common.inflate
import com.example.movieapp.common.loadUrl
import kotlinx.android.synthetic.main.single_movie.view.*

class PopularAdapter(private val movies : List<MovieDto>, private val listener : (MovieDto) -> Unit
) : RecyclerView.Adapter<PopularAdapter.ViewHolder>(){

    inner class ViewHolder(view : View) : RecyclerView.ViewHolder(view){
        fun bind(movie : MovieDto){
            itemView.movieTitle.text = movie.title
            //itemView.movieImage.loadUrl("https://image.tmdb.org/t/p/w185/${movie.frontImage}")
            itemView.movieImage.loadUrl("https://image.tmdb.org/t/p/w185/wlfDxbGEsW58vGhFljKkcR5IxDj.jpg")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view : View = parent.inflate(R.layout.single_movie, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = movies.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = movies[position]
        holder.bind(movie)
        holder.itemView.setOnClickListener { listener(movie) }
    }
}