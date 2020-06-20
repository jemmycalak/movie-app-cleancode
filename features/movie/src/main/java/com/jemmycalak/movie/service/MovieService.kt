package com.jemmycalak.movie.service

import androidx.lifecycle.Transformations
import com.jemmycalak.repository.MovieRepositoryInterface

class MovieService(private val repository: MovieRepositoryInterface) {

    suspend fun getListMovie(filter:Int, page:Int, shouldFetch:Boolean) =
        Transformations.map(repository.getListMovie(filter, page, shouldFetch)) { it }

    suspend fun getReviewMovie(idMovie:String)
            = Transformations.map(repository.getReviewMovie(idMovie)){it}

    fun getResult(id:Int?) = repository.getResult(id)

    fun getListFavorite() = repository.getListFavorite()

    fun updaResulte(result:Boolean?, id: Int?) = repository.updateFavorite(result,id)
}