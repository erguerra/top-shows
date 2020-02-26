package com.github.erguerra.topshows.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.github.erguerra.topshows.R

fun ImageView.load(url: String?){
    Glide.with(context)
        .load("https://image.tmdb.org/t/p/w300$url")
        .into(this)
}

@BindingAdapter("posterImage")
fun loadPoster(imageView: ImageView, posterPath: String?){
    Glide.with(imageView.context)
        .load("https://image.tmdb.org/t/p/w300$posterPath")
        .into(imageView)
}