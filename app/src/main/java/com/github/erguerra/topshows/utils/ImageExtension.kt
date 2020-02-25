package com.github.erguerra.topshows.utils

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.github.erguerra.topshows.R

fun ImageView.load(url: String){
    println("https://image.tmdb.org/t/p/w300$url")
    Glide.with(context)
        .load("https://image.tmdb.org/t/p/w300$url")
        .into(this)
}