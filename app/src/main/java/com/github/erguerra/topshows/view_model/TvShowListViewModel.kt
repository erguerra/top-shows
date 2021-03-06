package com.github.erguerra.topshows.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import androidx.paging.RxPagedListBuilder
import com.github.erguerra.topshows.model.TvShow
import com.github.erguerra.topshows.repository.network.TheMovieDBApi
import com.github.erguerra.topshows.repository.paging.TvShowDataSourceFactory
import com.github.erguerra.topshows.utils.INITIAL_LOAD_SIZE_HINT
import com.github.erguerra.topshows.utils.PAGE_SIZE
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class TvShowListViewModel(private val parameters: HashMap<String?, Any?>) : ViewModel(){

    var tvShowList: Observable<PagedList<TvShow>>
    private val compositeDisposable = CompositeDisposable()
    private val sourceFactory: TvShowDataSourceFactory

    init {
        sourceFactory = TvShowDataSourceFactory(compositeDisposable, TheMovieDBApi.getRetrofitServiceInstance(), parameters)
        val pagingConfig = PagedList.Config.Builder()
            .setPageSize(PAGE_SIZE)
            .setInitialLoadSizeHint(INITIAL_LOAD_SIZE_HINT)
            .setEnablePlaceholders(false)
            .build()

        tvShowList = RxPagedListBuilder(sourceFactory, pagingConfig)
            .setFetchScheduler(Schedulers.io())
            .buildObservable()
            .cache()
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

}
