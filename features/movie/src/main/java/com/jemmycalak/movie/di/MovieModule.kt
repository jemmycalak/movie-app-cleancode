package com.jemmycalak.movie.di

import com.jemmycalak.movie.service.MovieService
import com.jemmycalak.movie.viewmodel.CategoryMovieViewModel
import com.jemmycalak.movie.viewmodel.DetailMovieViewModel
import com.jemmycalak.movie.viewmodel.ListMovieViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val listMovieModule = module {

    factory { MovieService(get()) }

    viewModel { ListMovieViewModel(get(),get()) }
    viewModel { DetailMovieViewModel(get(), get()) }
    viewModel { CategoryMovieViewModel(get()) }
}