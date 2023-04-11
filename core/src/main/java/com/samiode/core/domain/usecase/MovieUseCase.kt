package com.samiode.core.domain.usecase

import com.samiode.core.domain.model.Cast
import com.samiode.core.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface MovieUseCase {
    fun getTrendingMovies(region: String): Flow<Result<List<Movie>>>

    fun getUpcomingMovies(region: String): Flow<Result<List<Movie>>>

    fun getPopularMovies(region: String): Flow<Result<List<Movie>>>

    fun getRecommendationMovies(id: Int): Flow<Result<List<Movie>>>

    fun getMovieCasts(id: Int): Flow<Result<List<Cast>>>

    fun getMovieByQuery(query: String): Flow<Result<List<Movie>>>

    fun getFavoriteMovies(): Flow<List<Movie>>

    fun isFavoriteMovie(id: Int): Flow<Boolean>

    suspend fun removeMovieFromFavorite(id: Int)

    suspend fun putMovieAsFavorite(movie: Movie)
}