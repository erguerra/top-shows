package com.github.erguerra.topshows.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.erguerra.topshows.model.Genre
import com.github.erguerra.topshows.repository.network.TheMovieDBApi
import com.github.erguerra.topshows.utils.formatDateToBrazilian
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.lang.Exception

class TvShowDetailsViewModel() : ViewModel(){

    private val _title = MutableLiveData<String>()
    private val _voteAverage = MutableLiveData<Double>()
    private val _rating = MutableLiveData<Float>()
    private val _posterPath = MutableLiveData<String>()
    private val _firstAirDate = MutableLiveData<String>()
    private val _overview = MutableLiveData<String>()
    private val _genres = MutableLiveData<String>()


    val title : LiveData<String>
    get() = _title

    val voteAverage : LiveData<Double>
    get() = _voteAverage

    val rating : LiveData<Float>
    get() = _rating

    val posterPath : LiveData<String>
    get() = _posterPath

    val firstAirDate : LiveData<String>
    get() = _firstAirDate

    val overview : LiveData<String>
    get() = _overview

    val genres : LiveData<String>
    get() = _genres


    private var detailsViewModelJob = Job()

    private val coroutineScope = CoroutineScope(detailsViewModelJob + Dispatchers.Main)


    init {
        getTvShowDetails(93533)
    }

    private fun getTvShowDetails(characterId: Int) {
        coroutineScope.launch {
            var getTvShowDetails = TheMovieDBApi
                .getRetrofitServiceInstance()
                .getDetailsByShowId(characterId)

            try {
                var response = getTvShowDetails.await()
                _title.value = response.title
                _voteAverage.value = response.voteAverage
                _rating.value = (response.voteAverage/2).toFloat()
                _posterPath.value = response.posterPath
                _firstAirDate.value = formatDateToBrazilian(response.firstAirDate)
                _overview.value = response.overview
                _genres.value = formatGenres(response.genres)
                
            } catch (e: Exception) {
                _title.value = "Failure: ${e.message}"
            }
        }
    }

    private fun formatGenres(genres: List<Genre>) : String{
        val genresNames =  genres.map { genre -> genre.name }
        return "${genresNames.toString().removePrefix("[").removeSuffix("]")}"
    }


}