package com.samiode.core.di

import com.samiode.core.data.MovieRepository
import com.samiode.core.domain.repository.IMovieRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module(includes = [ApiModule::class, DatabaseModule::class])
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun provideMovieRepository(movieRepository: MovieRepository): IMovieRepository
}