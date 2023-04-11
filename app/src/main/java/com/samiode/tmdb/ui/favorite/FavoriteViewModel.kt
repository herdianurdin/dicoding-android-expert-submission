package com.samiode.tmdb.ui.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.samiode.tmdb.domain.model.Movie
import com.samiode.tmdb.domain.usecase.MovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(private val movieUseCase: MovieUseCase): ViewModel() {
    fun getFavoriteMovies(): LiveData<List<Movie>> =
        movieUseCase.getFavoriteMovies().asLiveData()
}