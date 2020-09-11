package com.erguerra.dev.data_remote.`data-source`

import androidx.paging.DataSource
import com.erguerra.dev.data_remote.TMDBApi
import com.erguerra.dev.data_remote.model.RemoteTvShow
import io.reactivex.disposables.CompositeDisposable

class TvShowDataSourceFactory (
    private val compositeDisposable: CompositeDisposable,
    private val tmdbApi: TMDBApi,
    private val params: HashMap<String?, Any?>
) : DataSource.Factory<Int, RemoteTvShow>(){
    override fun create(): DataSource<Int, RemoteTvShow> {
        return TvShowDataSource(compositeDisposable, tmdbApi, params)
    }
}