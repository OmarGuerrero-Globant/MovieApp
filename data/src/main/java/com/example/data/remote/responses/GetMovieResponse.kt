package com.example.data.remote.responses

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GetMovieResponse(
    val popularity : Float,
    @SerializedName("vote_count") val voteCount : Int,
    val video : Boolean,
    @SerializedName("poster_path") val posterPath : String,
    val id : Int,
    val adult : Boolean,
    @SerializedName("backdrop_path") val backdropPath : String,
    @SerializedName("original_language") val originalLanguage : String,
    @SerializedName("original_title") val originalTitle : String,
    @SerializedName("genre_ids") val genreIds : List<Int>,
    val title : String,
    @SerializedName("vote_average") val voteAverage : Int,
    val overview : String,
    @SerializedName("release_date") val releaseDate : String
) : Parcelable