package com.erguerra.dev.data_remote.`data-source`

import androidx.paging.PageKeyedDataSource
import com.erguerra.dev.data_remote.FIRST_PAGE
import com.erguerra.dev.data_remote.TMDBApi
import com.erguerra.dev.data_remote.model.RemoteTvShow
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

class RelatedTvShowDataSource(private val compositeDisposable: CompositeDisposable, private val tmdbApi: TMDBApi, private val tvShowId: Int) : PageKeyedDataSource<Int, RemoteTvShow>() {

    private val params: HashMap<String?, Any?> = hashMapOf()

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, RemoteTvShow>
    ) {
        createObservable(FIRST_PAGE, FIRST_PAGE + 1, initialCallback = callback)
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, RemoteTvShow>) {
        val page: Int = params.key
        createObservable(page, page+1, callback = callback)
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, RemoteTvShow>) {
        val page: Int = params.key
        createObservable(page, page - 1, callback = callback)
    }

    private fun createObservable(requestedPage: Int, adjacentPage: Int, initialCallback: LoadInitialCallback<Int, RemoteTvShow>? = null, callback: LoadCallback<Int, RemoteTvShow>? = null) {
        params.put("page", requestedPage)
        val disposable : Disposable = tmdbApi.getRelatedTvShowsById(tvShowId, params).subscribe({
            initialCallback?.onResult(it.results, null, adjacentPage)
            callback?.onResult(it.results, adjacentPage)
        }, {throw it})

        compositeDisposable.add(disposable)
    }

}