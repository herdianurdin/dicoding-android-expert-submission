package com.samiode.core.data

import com.samiode.core.data.source.local.room.MovieDao
import com.samiode.core.data.source.remote.network.ApiService
import com.samiode.core.domain.model.Cast
import com.samiode.core.domain.model.Movie
import com.samiode.core.domain.repository.IMovieRepository
import com.samiode.core.utils.ObjectExtension.toCast
import com.samiode.core.utils.ObjectExtension.toMovieEntity
import com.samiode.core.utils.ObjectExtension.toMovie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.net.URLEncoder
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRepository @Inject constructor(
    private val apiService: ApiService,
    private val movieDao: MovieDao
): IMovieRepository{
    override fun getTrendingMovies(region: String): Flow<Result<List<Movie>>> = flow {
        val movies = mutableListOf<Movie>()

        val response = apiService.getTrendingMovies(region).results
        response?.forEach { movieResponse -> movies.add(movieResponse.toMovie()) }

        emit(Result.success(movies))
    }
        .catch { e -> emit(Result.failure(e)) }
        .flowOn(Dispatchers.IO)

    override fun getUpcomingMovies(region: String): Flow<Result<List<Movie>>> = flow {
        val movies = mutableListOf<Movie>()

        val response = apiService.getUpComingMovies(region).results
        response?.forEach { movieResponse -> movies.add(movieResponse.toMovie()) }

        emit(Result.success(movies))
    }
        .catch { e -> emit(Result.failure(e)) }
        .flowOn(Dispatchers.IO)

    override fun getPopularMovies(region: String): Flow<Result<List<Movie>>> = flow {
        val movies = mutableListOf<Movie>()

        val response = apiService.getPopularMovies(region).results
        response?.forEach { movieResponse -> movies.add(movieResponse.toMovie()) }

        emit(Result.success(movies))
    }
        .catch { e -> emit(Result.failure(e)) }
        .flowOn(Dispatchers.IO)

    override fun getRecommendationMovies(id: Int): Flow<Result<List<Movie>>> = flow {
        val movies = mutableListOf<Movie>()

        val response = apiService.getRecommendationMovies(id).results
        response?.forEach { movieResponse -> movies.add(movieResponse.toMovie()) }

        emit(Result.success(movies))
    }
        .catch { e -> emit(Result.failure(e)) }
        .flowOn(Dispatchers.IO)

    override fun getMovieCasts(id: Int): Flow<Result<List<Cast>>> = flow {
        val casts = mutableListOf<Cast>()

        val response = apiService.getMovieCasts(id).cast
        response.forEach { castResponse -> casts.add(castResponse.toCast()) }

        emit(Result.success(casts))
    }
        .catch { e -> emit(Result.failure(e)) }
        .flowOn(Dispatchers.IO)

    override fun getMovieByQuery(query: String): Flow<Result<List<Movie>>> = flow {
        val movies = mutableListOf<Movie>()
        val encodedQuery = URLEncoder.encode(query, "utf-8")

        val response = apiService.getMovieByQuery(encodedQuery).results
        response?.forEach { movieResponse -> movies.add(movieResponse.toMovie()) }

        emit(Result.success(movies))
    }
        .catch { e -> emit(Result.failure(e)) }
        .flowOn(Dispatchers.IO)

    override fun getFavoriteMovies(): Flow<List<Movie>> = flow {
        val movies = mutableListOf<Movie>()

        movieDao.getFavoriteMovies().collect { movieEntities ->
            movieEntities.forEach { movieEntity -> movies.add(movieEntity.toMovie()) }
            emit(movies)
        }
    }.flowOn(Dispatchers.IO)

    override fun isFavoriteMovies(id: Int): Flow<Boolean> =
        movieDao.isFavoriteMovie(id)

    override suspend fun removeMovieFromFavorite(id: Int) {
        movieDao.removeMovieFromFavorite(id)
    }

    override suspend fun putMovieAsFavorite(movie: Movie) {
        movieDao.putMovieAsFavorite(movie.toMovieEntity())
    }
}