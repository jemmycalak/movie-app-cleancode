package com.jemmycalak.movie.viewmodel

import android.content.Context
import com.jemmycalak.common.base.BaseViewModel
import com.jemmycalak.model.CategoryMovie
import com.jemmycalak.movie.R

class CategoryMovieViewModel(
    private val context:Context
) : BaseViewModel() {

    val data = arrayListOf<CategoryMovie>()
    init {
        setData()
    }

    fun setData(){
        data.add(CategoryMovie(0, context.getString(R.string.label_popular)))
        data.add(CategoryMovie(1, context.getString(R.string.label_upcoming)))
        data.add(CategoryMovie(2, context.getString(R.string.label_toprated)))
        data.add(CategoryMovie(3, context.getString(R.string.label_nowplaying)))
    }
}