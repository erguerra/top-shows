package com.github.erguerra.topshows.view_model

import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import androidx.paging.RxPagedListBuilder
import com.erguerra.dev.data_remote.TMDBApi
import com.erguerra.dev.data_remote.TMDBServiceBuilder
import com.github.erguerra.topshows.model.TvShow

import com.erguerra.dev.data_remote.`data-source`.RelatedTvShowDataSourceFactory
import com.github.erguerra.topshows.utils.API_KEY
import com.github.erguerra.topshows.utils.BASE_URL

import com.github.erguerra.topshows.utils.INITIAL_LOAD_SIZE_HINT
import com.github.erguerra.topshows.utils.PAGE_SIZE
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class RelatedTvShowsListViewModel(tvShowId: Int) : ViewModel() {

    var relatedTvShowsList: Observable<PagedList<TvShow>>
    private val compositeDisposable = CompositeDisposable()
    private val sourceFactory: RelatedTvShowDataSourceFactory

    init {
        sourceFactory = RelatedTvShowDataSourceFactory(compositeDisposable, TMDBServiceBuilder.invoke(
            BASE_URL, API_KEY), tvShowId)
        val pagingConfig = PagedList.Config.Builder()
            .setPageSize(PAGE_SIZE)
            .setInitialLoadSizeHint(INITIAL_LOAD_SIZE_HINT)
            .setEnablePlaceholders(false)
            .build()

        relatedTvShowsList = RxPagedListBuilder(sourceFactory, pagingConfig)
            .setFetchScheduler(Schedulers.io())
            .buildObservable()
            .cache()

    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

}