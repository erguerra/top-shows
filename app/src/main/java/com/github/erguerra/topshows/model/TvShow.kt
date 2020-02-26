package com.github.erguerra.topshows.model

import com.google.gson.annotations.SerializedName


data class TvShow (
    @SerializedName("name") val title: String,
    @SerializedName("vote_average") val voteAverage: Double,
    @SerializedName("poster_path") val posterPath: String,
    @SerializedName("first_air_date") val firstAirDate: String,
    val overview : String,
    val genres : List<Genre>,
    val id : Int
)