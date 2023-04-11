package com.samiode.tmdb.di

import android.content.Context
import androidx.room.Room
import com.samiode.tmdb.data.source.local.room.AppDatabase
import com.samiode.tmdb.data.source.local.room.MovieDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Provides
    fun provideMovieDao(appDatabase: AppDatabase): MovieDao = appDatabase.movieDao()

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "movie_database"
        ).build()
}