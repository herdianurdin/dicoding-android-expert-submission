package com.samiode.tmdb.utils

import com.samiode.tmdb.adapter.MovieHorizontalAdapter
import com.samiode.tmdb.adapter.MovieVerticalAdapter
import com.samiode.tmdb.domain.model.Movie

object AdapterExtension {
    fun MovieVerticalAdapter.setClickCallback(clickCallback: (movie: Movie) -> Unit) {
        this.setOnItemClickCallback(object: MovieVerticalAdapter.OnItemClickCallback {
            override fun onItemClickCallback(movie: Movie) { clickCallback(movie) }
        })
    }

    fun MovieHorizontalAdapter.setClickCallback(clickCallback: (movie: Movie) -> Unit) {
        this.setOnItemClickCallback(object: MovieHorizontalAdapter.OnItemClickCallback {
            override fun onItemClickCallback(movie: Movie) { clickCallback(movie) }
        })
    }
}