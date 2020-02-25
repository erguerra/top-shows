package com.github.erguerra.topshows.repository.paging


import android.util.Log
import androidx.paging.PageKeyedDataSource
import com.github.erguerra.topshows.model.ListResponse
import com.github.erguerra.topshows.model.TvShow
import com.github.erguerra.topshows.repository.network.TheMovieDBApi
import com.github.erguerra.topshows.utils.FIRST_PAGE
import io.reactivex.disposables.CompositeDisposable


class TvShowDataSource(private val theMovieDBApi: TheMovieDBApi,
                       private val compositeDisposable: CompositeDisposable,
                       private val params: HashMap<String?, Any?>) : PageKeyedDataSource<Int, TvShow>() {

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, TvShow>
    ) {

        val numberOfItems = params.requestedLoadSize
        createObservable(FIRST_PAGE, FIRST_PAGE + 1, callback, null)

    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, TvShow>) {
        val page = params.key
        val numberOfItems = params.requestedLoadSize
        createObservable(page, page + 1, null, callback)
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, TvShow>) {
        val page = params.key
        val numberOfItems = params.requestedLoadSize
        createObservable(page, page - 1,  null, callback)
    }



    private fun createObservable(requestedPage: Int,
                                 adjacentPage: Int,
                                 initialCallback: LoadInitialCallback<Int, TvShow>?,
                                 callback: LoadCallback<Int, TvShow>?) {

        params.put("page", requestedPage)
        compositeDisposable.add(
            theMovieDBApi.getPopularTvShows(params).subscribe({
                t: ListResponse<TvShow> ->  initialCallback?.onResult(t.results, null, adjacentPage)
                println(t.results)
                callback?.onResult(t.results, adjacentPage)
            }, {
                println("To Ferrado")
            }))
    }


}