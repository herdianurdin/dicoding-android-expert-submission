package com.samiode.tmdb.domain.usecase

import com.samiode.tmdb.domain.model.Cast
import com.samiode.tmdb.domain.model.Movie
import com.samiode.tmdb.domain.repository.IMovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieInteractor @Inject constructor(private val movieRepository: IMovieRepository):
MovieUseCase {
    override fun getNowPlayingMovies(region: String): Flow<Result<List<Movie>>> =
        movieRepository.getNowPlayingMovies(region)

    override fun getTrendingMovies(region: String): Flow<Result<List<Movie>>> =
        movieRepository.getTrendingMovies(region)

    override fun getUpcomingMovies(region: String): Flow<Result<List<Movie>>> =
        movieRepository.getUpcomingMovies(region)

    override fun getPopularMovies(region: String): Flow<Result<List<Movie>>> =
        movieRepository.getPopularMovies(region)

    override fun getRelatedMovies(id: Int): Flow<Result<List<Movie>>> =
        movieRepository.getRelatedMovies(id)

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