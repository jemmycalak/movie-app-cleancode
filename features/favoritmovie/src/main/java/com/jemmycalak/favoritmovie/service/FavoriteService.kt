package com.jemmycalak.favoritmovie.service

import com.jemmycalak.repository.MovieRepositoryInterface

class FavoriteService(private val listener:MovieRepositoryInterface) {

    fun getListFavorite()=listener.getListFavorite()
}