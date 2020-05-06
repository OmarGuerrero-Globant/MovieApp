package com.example.movieapp.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.LayoutRes
import com.bumptech.glide.Glide

fun ImageView.loadUrl(url : String){
    Glide.with(context).load(url).into(this)
}

fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachRoot: Boolean = true) : View =
    LayoutInflater.from(context).inflate(layoutRes, this, attachRoot)

inline fun <reified T : Activity> Context.intent(body: Intent.() -> Unit) : Intent =
    Intent(this, T::class.java).apply(body)

inline fun <reified T : Activity> Context.goToActivity(body : Intent.() -> Unit){
    startActivity(intent<T>(body))
}