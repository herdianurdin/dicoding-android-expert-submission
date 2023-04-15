package com.samiode.tmdb.utils

import com.samiode.core.domain.model.Cast
import com.samiode.core.domain.model.Movie

object DataDummy {
    val exception: Exception = Exception("failed")

    fun generateMovies(): List<Movie> {
        val movies = mutableListOf<Movie>()
        for (i in 0..6) {
            val movie = Movie(
                id = i,
                title = "The Movie $i",
                overview = "Overview the movie $i",
                posterPath = "Poster path movie $i",
                voteAverage = 2.5
            )
            movies.add(movie)
        }

        return movies
    }

    fun generateCasts(): List<Cast> {
        val casts = mutableListOf<Cast>()
        for (i in 0 .. 3) {
            val cast = Cast(
                id = i,
                name = "Cast $i",
                character = "Character $i",
                profilePath = "Profile path cast $i"
            )
            casts.add(cast)
        }

        return casts
    }
}