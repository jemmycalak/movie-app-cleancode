package com.jemmycalak.repository.di

import com.jemmycalak.repository.MovieRepository
import com.jemmycalak.repository.MovieRepositoryInterface
import com.jemmycalak.repository.utils.AppDispatchers
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module

val repositoryModule = module {

    factory { AppDispatchers(Dispatchers.Main, Dispatchers.IO) }
    factory { MovieRepository(get(), get(), get()) as MovieRepositoryInterface }
}