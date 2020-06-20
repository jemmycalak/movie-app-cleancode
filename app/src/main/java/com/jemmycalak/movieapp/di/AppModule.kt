package com.jemmycalak.movieapp.di

import com.jemmycalak.favoritmovie.di.favoriteModule
import com.jemmycalak.local.di.localModule
import com.jemmycalak.movie.di.listMovieModule
import com.jemmycalak.remote.di.remoteModule
import com.jemmycalak.repository.di.repositoryModule
import org.koin.core.module.Module

val appComponent : List<Module> = listOf(
    remoteModule,
    localModule,
    repositoryModule,
    listMovieModule,
    favoriteModule
)