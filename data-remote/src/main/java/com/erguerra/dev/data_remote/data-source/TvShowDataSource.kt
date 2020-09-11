package com.erguerra.dev.data_remote.`data-source`

import androidx.paging.PageKeyedDataSource
import com.erguerra.dev.data_remote.FIRST_PAGE
import com.erguerra.dev.data_remote.TMDBApi
import com.erguerra.dev.data_remote.model.ListResponse
import com.erguerra.dev.data_remote.model.RemoteTvShow
import io.reactivex.disposables.CompositeDisposable

class TvShowDataSource(
    private val compositeDisposable: CompositeDisposable,
    private val tmdbApi: TMDBApi,
    private val params: HashMap<String?, Any?>
) : PageKeyedDataSource<Int, RemoteTvShow>() {

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, RemoteTvShow>
    ) {
        createObservable(FIRST_PAGE, FIRST_PAGE + 1, initialCallback = callback)
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, RemoteTvShow>) {
        val page = params.key
        createObservable(page, page + 1, callback = callback)
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, RemoteTvShow>) {
        val page = params.key
        createObservable(page, page - 1, callback = callback)
    }

    private fun createObservable(requestedPage: Int, adjacentPage: Int, initialCallback: LoadInitialCallback<Int, RemoteTvShow>? = null,  callback: LoadCallback<Int, RemoteTvShow>? = null) {
        params.put("page", requestedPage)

        val disposable =
            tmdbApi.getPopularTvShows(params).subscribe({ t: ListResponse<RemoteTvShow> ->
                initialCallback?.onResult(t.results, null, adjacentPage)
                callback?.onResult(t.results, adjacentPage)
            }, {
                throw it
            })

        compositeDisposable.add(disposable)
    }

}