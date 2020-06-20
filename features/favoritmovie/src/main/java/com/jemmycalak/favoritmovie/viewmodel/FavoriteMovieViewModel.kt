package com.jemmycalak.favoritmovie.viewmodel

import androidx.lifecycle.LiveData
import com.jemmycalak.common.base.BaseViewModel
import com.jemmycalak.favoritmovie.fragment.ListFavoritMovieFragmentDirections
import com.jemmycalak.favoritmovie.service.FavoriteService
import com.jemmycalak.model.Result

class FavoriteMovieViewModel(private val service:FavoriteService):BaseViewModel() {

    init {
        getData()
    }

    lateinit var data : LiveData<List<Result>>

    fun getData(){
        data = service.getListFavorite()
    }

    fun onDetailMovie(it: Result) {
        navigateTo(ListFavoritMovieFragmentDirections.listMovieToDetailMovie(it))
    }

}