package com.github.erguerra.topshows.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ListResponse<T>(
    val page: Int,
    @SerializedName("total_results") val totalResults: Double,
    @SerializedName("total_pages") val totalPages: Double,
    val results: List<T>
) : Serializable