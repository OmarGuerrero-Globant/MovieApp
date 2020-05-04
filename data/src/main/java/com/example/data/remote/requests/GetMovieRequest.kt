package com.example.data.remote.requests

import com.google.gson.annotations.SerializedName

data class GetMovieRequest(
    @SerializedName("id") val id : String)