package com.samiode.tmdb.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.samiode.core.domain.model.Cast
import com.samiode.core.domain.model.Movie
import com.samiode.core.domain.usecase.MovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val movieUseCase: MovieUseCase): ViewModel() {
    fun getMovieCasts(id: Int): LiveData<Result<List<Cast>>> =
        movieUseCase.getMovieCasts(id).asLiveData()

    fun getRecommendationMovies(id: Int): LiveData<Result<List<Movie>>> =
        movieUseCase.getRecommendationMovies(id).asLiveData()

    fun isFavoriteMovie(id: Int): LiveData<Boolean> =
        movieUseCase.isFavoriteMovie(id).asLiveData()

    fun putMovieAsFavorite(movie: Movie) {
        viewModelScope.launch {
            movieUseCase.putMovieAsFavorite(movie)
        }
    }

    fun removeMovieFromFavorite(id: Int) {
        viewModelScope.launch {
            movieUseCase.removeMovieFromFavorite(id)
        }
    }
}