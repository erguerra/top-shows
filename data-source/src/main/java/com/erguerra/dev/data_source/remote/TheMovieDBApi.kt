package com.erguerra.dev.data_source.remote

interface TheMovieDBApi {
    @GET("/3/tv/popular")
    fun getPopularTvShows(@QueryMap parameters: HashMap<String?, Any?>) : Observable<ListResponse<TvShow>>

    @GET("/3/tv/{tv_id}")
    fun getDetailsByShowIdAsync(@Path("tv_id") tvShowId: Int) : Deferred<TvShow>

    @GET("/3/tv/{tv_id}/similar")
    fun getRelatedTvShowsById(@Path("tv_id") tvShowId: Int, @QueryMap parameters: HashMap<String?, Any?>) : Observable<ListResponse<TvShow>>
}