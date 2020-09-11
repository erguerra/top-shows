package com.erguerra.dev.data.model

import com.erguerra.dev.data_remote.model.RemoteTvShow

data class TvShow(
    val title: String,
    val voteAverage: Double,
    val posterPath: String,
    val firstAirDate: String,
    val overview: String,
    val genres: List<Genre>,
    val id: Int
)

fun RemoteTvShow.toTvShow() = TvShow(
        title = this.title,
        voteAverage = this.voteAverage,
        posterPath = this.posterPath,
        firstAirDate = this.firstAirDate,
        overview = this.overview,
        genres = this.genres.map { it.toGenre() },
        id = this.id
    )
