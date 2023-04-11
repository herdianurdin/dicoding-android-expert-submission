package com.samiode.core.utils

import com.samiode.core.data.source.local.entity.MovieEntity
import com.samiode.core.data.source.remote.response.CastResponse
import com.samiode.core.data.source.remote.response.MovieResponse
import com.samiode.core.domain.model.Cast
import com.samiode.core.domain.model.Movie

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