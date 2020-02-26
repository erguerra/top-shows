package com.github.erguerra.topshows.repository.paging

import androidx.paging.DataSource
import com.github.erguerra.topshows.model.TvShow
import com.github.erguerra.topshows.repository.network.TheMovieDBApi
import io.reactivex.disposables.CompositeDisposable

class TvShowDataSourceFactory (
    private val compositeDisposable: CompositeDisposable,
    private val theMovieDBApi: TheMovieDBApi,
    private val params: HashMap<String?, Any?>
): DataSource.Factory<Int, TvShow>(){
    override fun create(): DataSource<Int, TvShow> {
        return TvShowDataSource(compositeDisposable, theMovieDBApi, params)
    }
}