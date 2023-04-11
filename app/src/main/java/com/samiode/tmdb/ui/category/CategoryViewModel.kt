package com.samiode.tmdb.ui.category

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.samiode.core.domain.model.Movie
import com.samiode.core.domain.usecase.MovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(private val movieUseCase: MovieUseCase): ViewModel() {
    fun getTrendingMovies(region: String): LiveData<Result<List<Movie>>> =
        movieUseCase.getTrendingMovies(region).asLiveData()

    fun getPopularMovies(region: String): LiveData<Result<List<Movie>>> =
        movieUseCase.getPopularMovies(region).asLiveData()

    fun getUpcomingMovies(region: String): LiveData<Result<List<Movie>>> =
        movieUseCase.getUpcomingMovies(region).asLiveData()
}