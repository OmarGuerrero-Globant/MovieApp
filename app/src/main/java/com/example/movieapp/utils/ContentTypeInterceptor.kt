package com.example.movieapp.utils

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class ContentTypeInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        return chain.proceed( request.newBuilder()
            .addHeader("Accept", "application/json")
            .addHeader("Content-type", "application/json")
            .method(request.method(), request.body())
            .build() )
    }
}