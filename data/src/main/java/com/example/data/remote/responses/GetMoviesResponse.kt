package com.example.data.remote.responses

import com.google.gson.annotations.SerializedName

data class GetMoviesResponse(
    @SerializedName("page")var page : Int?,
    @SerializedName("results")var results : List<GetMovieResponse>?,
    @SerializedName("total_pages") var totalPages : Int?,
    @SerializedName("total_results") var totalResults : Int?
)