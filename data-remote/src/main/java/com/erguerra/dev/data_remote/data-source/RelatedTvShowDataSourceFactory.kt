package com.erguerra.dev.data_remote.`data-source`

import com.erguerra.dev.data_remote.TMDBApi
import com.erguerra.dev.data_remote.model.RemoteTvShow
import io.reactivex.disposables.CompositeDisposable
import androidx.paging.DataSource

class RelatedTvShowDataSourceFactory(
    private val compositeDisposable: CompositeDisposable,
    private val tmdbApi: TMDBApi,
    private val tvShowId: Int
) : DataSource.Factory<Int, RemoteTvShow>() {
    override fun create(): DataSource<Int, RemoteTvShow> {
        return RelatedTvShowDataSource(compositeDisposable, tmdbApi, tvShowId)
    }

}