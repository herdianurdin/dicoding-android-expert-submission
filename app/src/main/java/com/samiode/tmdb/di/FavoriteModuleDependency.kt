package com.samiode.tmdb.di

import com.samiode.tmdb.domain.usecase.MovieUseCase
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface FavoriteModuleDependency {
    fun movieUseCase(): MovieUseCase
}