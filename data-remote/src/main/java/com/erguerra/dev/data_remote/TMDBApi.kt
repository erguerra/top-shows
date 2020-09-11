package com.erguerra.dev.data_remote

import com.erguerra.dev.data_remote.model.ListResponse
import com.erguerra.dev.data_remote.model.RemoteTvShow
import io.reactivex.Observable
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap

interface TMDBApi {
    @GET("/3/tv/popular")
    fun getPopularTvShows(@QueryMap parameters: HashMap<String?, Any?>) : Observable<ListResponse<RemoteTvShow>>

    @GET("/3/tv/{tv_id}")
    fun getDetailsByShowIdAsync(@Path("tv_id") tvShowId: Int) : Deferred<RemoteTvShow>

    @GET("/3/tv/{tv_id}/similar")
    fun getRelatedTvShowsById(@Path("tv_id") tvShowId: Int, @QueryMap parameters: HashMap<String?, Any?>) : Observable<ListResponse<RemoteTvShow>>
}