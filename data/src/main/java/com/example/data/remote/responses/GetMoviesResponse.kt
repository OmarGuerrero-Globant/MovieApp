package com.example.data.remote.responses

import com.google.gson.annotations.SerializedName

data class GetMoviesResponse(
    @SerializedName("page")val page : Int?,
    @SerializedName("results")val results : List<GetMovieResponse>?,
    @SerializedName("total_pages") val totalPages : Int?,
    @SerializedName("total_results") val totalResults : Int?
)