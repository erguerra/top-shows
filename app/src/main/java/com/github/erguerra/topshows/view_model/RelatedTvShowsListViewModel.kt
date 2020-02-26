package com.github.erguerra.topshows.view_model

import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import androidx.paging.RxPagedListBuilder
import com.github.erguerra.topshows.model.TvShow
import com.github.erguerra.topshows.repository.network.TheMovieDBApi
import com.github.erguerra.topshows.repository.paging.RelatedTvShowsDataSourceFactory
import com.github.erguerra.topshows.utils.INITIAL_LOAD_SIZE_HINT
import com.github.erguerra.topshows.utils.PAGE_SIZE
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class RelatedTvShowsListViewModel(private val parameters: HashMap<String?, Any?>, tvShowId: Int) : ViewModel() {

    var relatedTvShowsList: Observable<PagedList<TvShow>>
    private val compositeDisposable = CompositeDisposable()
    private val sourceFactory: RelatedTvShowsDataSourceFactory

    init {
        sourceFactory = RelatedTvShowsDataSourceFactory(compositeDisposable, TheMovieDBApi.getRetrofitServiceInstance(), parameters, tvShowId)
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