package com.erguerra.dev.data.repository

interface RelatedTvShowsRepository {
    fun loadRelatedTvShows(tvShowId: Int)
}