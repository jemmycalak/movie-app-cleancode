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
import com.jemmycalak.model.VideoMovie
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
        resultId?.let {
            model = service.getResult(resultId)
            getReviewMovie(resultId.toString())
            getTrailerMovie(resultId)
        }
    }

    fun onFavorit(isFavorite:Boolean) {
        service.updaResulte(!isFavorite, model.value?.id)
    }

    private val _listReviewMovie = MediatorLiveData<Resource<ReviewMovie>>()
    val listReviewMovieData: LiveData<Resource<ReviewMovie>> get() = _listReviewMovie
    private var listReviewMovieResource: LiveData<Resource<ReviewMovie>> = MutableLiveData()
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


    private var trailerMovieResource: LiveData<Resource<VideoMovie>> = MutableLiveData()
    private val _trailerMovie = MediatorLiveData<Resource<VideoMovie>>()
    val trailerMovieData: LiveData<Resource<VideoMovie>> get() = _trailerMovie
    private fun getTrailerMovie(movieId: Int) {
        viewModelScope.launch(dispatchers.main) {
            _trailerMovie.removeSource(trailerMovieResource)
            withContext(dispatchers.io) {
                trailerMovieResource = service.getMovieTrailer(movieId)
            }
            _trailerMovie.addSource(trailerMovieResource) {
                _trailerMovie.postValue(it)
                when (it.status) {
                    Resource.Status.SUCCESS -> {
                        _trailerMovie.postValue(it)
                    }
                    Resource.Status.ERROR -> _errorHandler.postValue(Event(ErrorHandler(ErrorHandler.ErrorType.LAYOUT, it.error)))
                }
            }
        }
    }

}