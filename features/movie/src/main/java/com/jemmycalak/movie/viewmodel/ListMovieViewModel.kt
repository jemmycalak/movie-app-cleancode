package com.jemmycalak.movie.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.jemmycalak.common.base.BaseViewModel
import com.jemmycalak.common.utils.ErrorHandler
import com.jemmycalak.common.utils.Event
import com.jemmycalak.model.MovieModel
import com.jemmycalak.model.Result
import com.jemmycalak.movie.fragment.ListMovieFragmentDirections
import com.jemmycalak.movie.service.MovieService
import com.jemmycalak.repository.utils.AppDispatchers
import com.jemmycalak.repository.utils.Resource
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ListMovieViewModel(
    private val service: MovieService,
    private val dispatchers: AppDispatchers
) : BaseViewModel() {

    private val _listMovie = MediatorLiveData<Resource<MovieModel>>()
    var listMovieResource: LiveData<Resource<MovieModel>> = MutableLiveData()
    val listMovieData: LiveData<Resource<MovieModel>> get() = _listMovie
    val loaderListMovie = MutableLiveData<Boolean>(true)
    var isAvailableLoadMore = false
    var filterId = 0
    var page = 1

    init {
        getListMovie(filterId, page, true)
    }

    fun getListMovie(
        filter: Int, pages: Int,
        shouldFetch: Boolean,
        shouldResetData: Boolean = false
    ) {
        if (shouldResetData) {
            _listMovie.value = null
        }
        filterId = filter
        page = pages
        viewModelScope.launch(dispatchers.main) {
            _listMovie.removeSource(listMovieResource)
            withContext(dispatchers.io) {
                listMovieResource = service(filter, pages, shouldFetch)
            }
            _listMovie.addSource(listMovieResource) {
                isAvailableLoadMore = (it.data?.page ?: 0) <= (it.data?.totalPages ?: 0)
                loaderListMovie.value = (it.status == Resource.Status.LOADING)
                when (it.status) {
                    Resource.Status.SUCCESS -> {
                        _listMovie.postValue(
                            it.copy(
                                data = it.data?.copy(
                                    results = _listMovie.value?.data?.results.orEmpty() + it?.data?.results.orEmpty()
                                )
                            )
                        )
                    }

                    Resource.Status.ERROR -> {
                        _errorHandler.postValue(
                            Event(
                                ErrorHandler(
                                    ErrorHandler.ErrorType.LAYOUT,
                                    it.error
                                )
                            )
                        )
                        it.data?.let { _ -> _listMovie.postValue(it) }
                    }
                }
            }
        }
    }

    fun onDetailMovie(result: Result) {
        navigateTo(ListMovieFragmentDirections.listMovieToDetailMovie(result))
    }

    fun onFavoritMovie() {
        navigateTo(ListMovieFragmentDirections.listMovieToListFavoritMovie())
    }

}