package com.samiode.tmdb.utils

import com.samiode.tmdb.data.source.local.entity.MovieEntity
import com.samiode.tmdb.data.source.remote.response.CastResponse
import com.samiode.tmdb.data.source.remote.response.MovieResponse
import com.samiode.tmdb.domain.model.Cast
import com.samiode.tmdb.domain.model.Movie

object ObjectExtension {
    fun MovieEntity.toMovie(): Movie =
        Movie(
            id = this.id,
            title = this.title,
            overview = this.overview,
            posterPath = this.posterPath,
            voteAverage = this.voteAverage
        )

    fun MovieResponse.toMovie(): Movie =
        Movie(
            id = this.id,
            title = this.title,
            overview = this.overview,
            posterPath = this.posterPath,
            voteAverage = this.voteAverage
        )

    fun Movie.toMovieEntity(): MovieEntity =
        MovieEntity(
            id = this.id,
            title = this.title,
            overview = this.overview,
            posterPath = this.posterPath.toString(),
            voteAverage = this.voteAverage
        )

    fun CastResponse.toCast(): Cast =
        Cast(
            id = this.id,
            name = this.name,
            character = this.character,
            profilePath = this.profilePath
        )
}