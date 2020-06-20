package com.jemmycalak.movie.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.jemmycalak.common.base.BaseViewModel
import com.jemmycalak.common.utils.ErrorHandler
import com.jemmycalak.common.utils.Event
import com.jemmycalak.model.Result
import com.jemmycalak.model.ReviewMovie
import com.jemmycalak.movie.service.MovieService
import com.jemmycalak.repository.utils.AppDispatchers
import com.jemmycalak.repository.utils.Resource
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailMovieViewModel(
    private val service: MovieService,
    private val dispatchers: AppDispatchers
) : BaseViewModel() {

    lateinit var model :LiveData<Result>

    fun getModel(resultId:Int?) {
        model = service.getResult(resultId)
        getReviewMovie(resultId.toString())
    }

    fun onFavorit(isFavorite:Boolean) {
        service.updaResulte(!isFavorite, model.value?.id)
    }

    val _listReviewMovie = MediatorLiveData<Resource<ReviewMovie>>()
    var listReviewMovieResource: LiveData<Resource<ReviewMovie>> = MutableLiveData()
    val listReviewMovieData: LiveData<Resource<ReviewMovie>> get() = _listReviewMovie
    val loaderListMovie: MutableLiveData<Boolean> = MutableLiveData()

    fun getReviewMovie(idMovie:String){
        viewModelScope.launch(dispatchers.main) {
            _listReviewMovie.removeSource(listReviewMovieResource)
            withContext(dispatchers.io) { listReviewMovieResource = service.getReviewMovie(idMovie) }
            _listReviewMovie.addSource(listReviewMovieResource) {
                _listReviewMovie.postValue(it)
                loaderListMovie.value = (it.status == Resource.Status.LOADING)
                when (it.status) {
                    Resource.Status.SUCCESS -> _listReviewMovie.postValue(it)
                    Resource.Status.ERROR -> _errorHandler.postValue(Event(ErrorHandler(ErrorHandler.ErrorType.LAYOUT, it.error)))
                }
            }
        }
    }

}