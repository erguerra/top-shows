package com.github.erguerra.topshows.repository.paging


import android.util.Log
import androidx.paging.PageKeyedDataSource
import com.github.erguerra.topshows.model.ListResponse
import com.github.erguerra.topshows.model.TvShow
import com.github.erguerra.topshows.repository.network.TheMovieDBApi
import com.github.erguerra.topshows.utils.FIRST_PAGE
import io.reactivex.disposables.CompositeDisposable


class TvShowDataSource(
    private val compositeDisposable: CompositeDisposable,
    private val theMovieDBApi: TheMovieDBApi,
    private val params: HashMap<String?, Any?>
) : PageKeyedDataSource<Int, TvShow>() {

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, TvShow>
    ) {
        createObservable(FIRST_PAGE, FIRST_PAGE + 1, initialCallback = callback)
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, TvShow>) {
        val page = params.key
        createObservable(page, page + 1, callback = callback)
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, TvShow>) {
        val page = params.key
        createObservable(page, page - 1, callback = callback)
    }

    private fun createObservable(
        requestedPage: Int,
        adjacentPage: Int,
        initialCallback: LoadInitialCallback<Int, TvShow>? = null,
        callback: LoadCallback<Int, TvShow>? = null
    ) {

        params.put("page", requestedPage)
        compositeDisposable.add(
            theMovieDBApi.getPopularTvShows(params).subscribe({
                initialCallback?.onResult(it.results, null, adjacentPage)
                callback?.onResult(it.results, adjacentPage)
            }, {
                Log.d("PRE", "Error reading page: $requestedPage")
            })
        )
    }


}