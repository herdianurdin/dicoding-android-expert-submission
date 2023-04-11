package com.samiode.tmdb.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.samiode.tmdb.domain.model.Cast
import com.samiode.tmdb.domain.model.Movie
import com.samiode.tmdb.domain.usecase.MovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val movieUseCase: MovieUseCase): ViewModel() {
    fun getMovieCasts(id: Int): LiveData<Result<List<Cast>>> =
        movieUseCase.getMovieCasts(id).asLiveData()

    fun getRelatedMovies(id: Int): LiveData<Result<List<Movie>>> =
        movieUseCase.getRelatedMovies(id).asLiveData()

    fun isFavoriteMovie(id: Int): LiveData<Boolean> =
        movieUseCase.isFavoriteMovie(id).asLiveData()

    fun putMovieAsFavorite(movie: Movie) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                movieUseCase.putMovieAsFavorite(movie)
            }
        }
    }

    fun removeMovieFromFavorite(id: Int) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                movieUseCase.removeMovieFromFavorite(id)
            }
        }
    }
}