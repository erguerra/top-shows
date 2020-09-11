package com.erguerra.dev.data_remote.model

import com.google.gson.annotations.SerializedName


data class RemoteTvShow (
    @SerializedName("name") val title: String,
    @SerializedName("vote_average") val voteAverage: Double,
    @SerializedName("poster_path") val posterPath: String,
    @SerializedName("first_air_date") val firstAirDate: String,
    val overview : String,
    val genres : List<RemoteGenre>,
    val id : Int
)