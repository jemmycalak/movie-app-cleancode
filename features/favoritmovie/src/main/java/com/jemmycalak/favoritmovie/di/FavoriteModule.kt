package com.jemmycalak.favoritmovie.di

import com.jemmycalak.favoritmovie.service.FavoriteService
import com.jemmycalak.favoritmovie.viewmodel.FavoriteMovieViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val favoriteModule = module {
    factory { FavoriteService(get()) }
    viewModel { FavoriteMovieViewModel(get()) }
}