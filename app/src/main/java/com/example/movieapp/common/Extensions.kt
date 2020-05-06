package com.example.movieapp.common

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
