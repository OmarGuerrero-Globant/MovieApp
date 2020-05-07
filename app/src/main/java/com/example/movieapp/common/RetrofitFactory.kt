package com.example.movieapp.common

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitFactory {

    private const val API_URL = "https://api.themoviedb.org/3/"
    private const val TIME_OUT_API = 30

    fun buildRetrofitService() : Retrofit{
        val gson : Gson = GsonBuilder()
            .setLenient()
            .create()

        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val okHttpClient =  OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor())
            .addInterceptor(ContentTypeInterceptor())
            .addInterceptor(logging)
            .build()

        return Retrofit.Builder()
            .baseUrl(API_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }
}