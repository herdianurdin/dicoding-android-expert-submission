package com.samiode.core.domain.usecase

import com.samiode.core.domain.model.Cast
import com.samiode.core.domain.model.Movie
import com.samiode.core.domain.repository.IMovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieInteractor @Inject constructor(private val movieRepository: IMovieRepository):
    MovieUseCase {
    override fun getTrendingMovies(region: String): Flow<Result<List<Movie>>> =
        movieRepository.getTrendingMovies(region)

    override fun getUpcomingMovies(region: String): Flow<Result<List<Movie>>> =
        movieRepository.getUpcomingMovies(region)

    override fun getPopularMovies(region: String): Flow<Result<List<Movie>>> =
        movieRepository.getPopularMovies(region)

    override fun getRecommendationMovies(id: Int): Flow<Result<List<Movie>>> =
        movieRepository.getRecommendationMovies(id)

    override fun getMovieCasts(id: Int): Flow<Result<List<Cast>>> =
        movieRepository.getMovieCasts(id)

    override fun getMovieByQuery(query: String): Flow<Result<List<Movie>>> =
        movieRepository.getMovieByQuery(query)

    override fun getFavoriteMovies(): Flow<List<Movie>> =
        movieRepository.getFavoriteMovies()

    override fun isFavoriteMovie(id: Int): Flow<Boolean> =
        movieRepository.isFavoriteMovies(id)

    override suspend fun removeMovieFromFavorite(id: Int) =
        movieRepository.removeMovieFromFavorite(id)

    override suspend fun putMovieAsFavorite(movie: Movie) =
        movieRepository.putMovieAsFavorite(movie)
}