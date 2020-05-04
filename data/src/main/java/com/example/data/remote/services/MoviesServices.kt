package com.example.data.remote.services

import com.example.data.remote.responses.GetMovieResponse
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesServices {

    @GET("movie/{id}?append_to_response=videos,credits,reviews")
    fun getMovieById(@Path("id") id : Long) : Single<Response<GetMovieResponse>>

    @GET("movie/popular")
    fun getMovies(@Query("page") page : Int) : Single<Response<List<GetMovieResponse>>>

}