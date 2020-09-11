package com.erguerra.dev.data_remote

import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

interface TMDBServiceBuilder {

    companion object {

        inline operator fun <reified S> invoke(baseUrl: String, authToken: String) : S{
            return buildRetrofitService(baseUrl, buildInterceptors(authToken)).create(S::class.java)
        }

        fun buildInterceptors(authToken: String) : OkHttpClient {
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY

            val httpClient = OkHttpClient.Builder()

            httpClient.addInterceptor(logging)
            httpClient.addInterceptor{chain ->
                val original = chain.request()
                val originalHttpUrl = original.url
                val url = originalHttpUrl.newBuilder()
                    .addQueryParameter("api_key", authToken)
                    .build()

                chain.proceed(original.newBuilder().url(url).build())
            }

            return httpClient.build()
        }

        fun buildRetrofitService(baseUrl: String, httpClient: OkHttpClient) : Retrofit{
            val gson = GsonBuilder().setLenient().create()
            return Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .baseUrl(baseUrl)
                .client(httpClient)
                .build()
        }
    }
}