package com.jemmycalak.movie.viewmodel

import android.view.View
import androidx.lifecycle.*
import com.jemmycalak.common.base.BaseViewModel
import com.jemmycalak.common.utils.ErrorHandler
import com.jemmycalak.common.utils.Event
import com.jemmycalak.model.MovieModel
import com.jemmycalak.model.Result
import com.jemmycalak.movie.fragment.CategoryMovieFragment
import com.jemmycalak.movie.fragment.ListMovieFragment
import com.jemmycalak.movie.fragment.ListMovieFragmentDirections
import com.jemmycalak.movie.service.MovieService
import com.jemmycalak.repository.utils.AppDispatchers
import com.jemmycalak.repository.utils.Resource
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ListMovieViewModel(private val service:MovieService,
                         private val dispatchers:AppDispatchers) : BaseViewModel(){

    val searchLoading: MutableLiveData<Boolean> = MutableLiveData()

    val _listMovie = MediatorLiveData<Resource<MovieModel>>()
    var listMovieResource: LiveData<Resource<MovieModel>> = MutableLiveData()
    val listMovieData: LiveData<Resource<MovieModel>> get() = _listMovie
    val loaderListMovie: MutableLiveData<Boolean> = MutableLiveData()

    init {
        getListMovie(0, 1, true)
    }

    fun getListMovie(filter:Int, page:Int, shouldFetch:Boolean) {
        viewModelScope.launch(dispatchers.main) {
            _listMovie.removeSource(listMovieResource)
            withContext(dispatchers.io) {
                listMovieResource = service(filter, page, shouldFetch)
            }
            _listMovie.addSource(listMovieResource) {
                _listMovie.postValue(it)
                loaderListMovie.value = (it.status == Resource.Status.LOADING)
                when (it.status) {
                    Resource.Status.SUCCESS -> _listMovie.postValue(it)
                    Resource.Status.ERROR -> _errorHandler.postValue(Event(ErrorHandler(ErrorHandler.ErrorType.LAYOUT, it.error)))
                }
            }
        }

    }

    fun onDetailMovie(result:Result){
        navigateTo(ListMovieFragmentDirections.listMovieToDetailMovie(result))
    }

    fun onFavoritMovie(){
        navigateTo(ListMovieFragmentDirections.listMovieToListFavoritMovie())
    }

}