package com.samiode.core.utils

import com.samiode.core.adapter.MovieHorizontalAdapter
import com.samiode.core.adapter.MovieVerticalAdapter
import com.samiode.core.domain.model.Movie

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