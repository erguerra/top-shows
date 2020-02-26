package com.github.erguerra.topshows.model

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.google.gson.annotations.SerializedName
import com.squareup.moshi.Json

data class TvShow (
    @SerializedName("name") val title: String,
    @SerializedName("vote_average") val voteAverage: Double,
    @SerializedName("poster_path") val posterPath: String,
    @SerializedName("first_air_date") val firstAirDate: String,
    val overview : String,
    val genres : List<Genre>,
    val id : Int
)