package com.example.movieapp.common

import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.text.DateFormat
import java.util.concurrent.TimeUnit


object RetrofitFactory {

    private const val API_URL = "https://api.themoviedb.org/3/"
    private const val TIME_OUT_API = 30

    fun buildRetrofitService() : Retrofit{
        val gson : Gson = GsonProvider.makeGson()

        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val okHttpClient =  OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor())
            .addInterceptor(ContentTypeInterceptor())
            .addInterceptor(logging)
            .followRedirects(true)
            .followSslRedirects(true)
            .retryOnConnectionFailure(true)
            .connectTimeout(TIME_OUT_API.toLong(), TimeUnit.SECONDS)
            .writeTimeout(TIME_OUT_API.toLong(), TimeUnit.SECONDS)
            .readTimeout(TIME_OUT_API.toLong(), TimeUnit.SECONDS)
            .build()

        return Retrofit.Builder()
            .baseUrl(API_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .build()
    }
}