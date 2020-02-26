package com.github.erguerra.topshows.repository.network

import com.github.erguerra.topshows.model.ListResponse
import com.github.erguerra.topshows.model.TvShow
import com.github.erguerra.topshows.utils.API_KEY
import com.github.erguerra.topshows.utils.BASE_URL
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.QueryMap
import kotlin.collections.HashMap
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import io.reactivex.Observable
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Path


interface TheMovieDBApi {

    @GET("/3/tv/popular")
    fun getPopularTvShows(@QueryMap parameters: HashMap<String?, Any?>) : Observable<ListResponse<TvShow>>

    @GET("/3/tv/{tv_id}")
    fun getDetailsByShowId(@Path("tv_id") tvShowId: Int) : Deferred<TvShow>

    @GET("/3/tv/{tv_id}/similar")
    fun getSimilarTvShows(@Path("tv_id") tvShowId: Int, @QueryMap parameters: HashMap<String?, Any?>) : Observable<ListResponse<TvShow>>

    companion object {
        fun getRetrofitServiceInstance() : TheMovieDBApi{

            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY

            val httpClient = OkHttpClient.Builder()

            httpClient.addInterceptor(logging)

            httpClient.addInterceptor{ chain ->
                val original = chain.request()
                val originalHttpUrl = original.url
                val url = originalHttpUrl.newBuilder()
                    .addQueryParameter("api_key", API_KEY)
                    .build()

                chain.proceed(original.newBuilder().url(url).build())
            }

            val gson = GsonBuilder().setLenient().create()

            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .baseUrl(BASE_URL)
                .client(httpClient.build())
                .build()

            return retrofit.create<TheMovieDBApi>(TheMovieDBApi::class.java)

        }
    }
}

