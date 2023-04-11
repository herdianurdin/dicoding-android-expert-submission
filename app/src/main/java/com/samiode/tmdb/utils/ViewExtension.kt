package com.samiode.tmdb.utils

import android.annotation.SuppressLint
import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.facebook.shimmer.ShimmerFrameLayout
import com.samiode.tmdb.R
import com.samiode.tmdb.adapter.CastAdapter
import com.samiode.tmdb.adapter.MovieHorizontalAdapter
import com.samiode.tmdb.adapter.MovieVerticalAdapter

object ViewExtension {
    @SuppressLint("CheckResult")
    fun ImageView.setImageFromUrl(url: String, circleCrop: Boolean = false) {
        val glide = Glide
            .with(this.context)
            .load(url)
            .placeholder(if (circleCrop) R.drawable.no_image_circle else R.drawable.no_image)
            .error(if (circleCrop) R.drawable.error_image_circle else R.drawable.error_image)
        if (circleCrop) glide.circleCrop()
        glide.into(this)
    }

    fun ImageView.parseMovieRating(rating: Int) {
        when (rating) {
            0 -> this.setImageResource(R.drawable.rating_0)
            1 -> this.setImageResource(R.drawable.rating_1)
            2 -> this.setImageResource(R.drawable.rating_2)
            3 -> this.setImageResource(R.drawable.rating_3)
            4 -> this.setImageResource(R.drawable.rating_4)
            5 -> this.setImageResource(R.drawable.rating_5)
            else -> throw IllegalArgumentException("Invalid rating argument!")
        }
    }

    fun RecyclerView.setVerticalMovieView(adapter: MovieVerticalAdapter) {
        this.apply {
            setAdapter(adapter)
            layoutManager = LinearLayoutManager(this.context)
        }
    }

    fun RecyclerView.setHorizontalMovieView(adapter: MovieHorizontalAdapter) {
        this.apply {
            setAdapter(adapter)
            layoutManager = LinearLayoutManager(this.context, RecyclerView.HORIZONTAL, false)
        }
    }

    fun RecyclerView.setCastMovieView(adapter: CastAdapter) {
        this.apply {
            setAdapter(adapter)
            layoutManager = LinearLayoutManager(this.context)
        }
    }

    fun View.setVisible(state: Boolean) {
        this.visibility = if (state) View.VISIBLE else View.GONE
    }

    fun ShimmerFrameLayout.setAvailability(state: Boolean) {
        this.apply {
            if (state) startShimmer() else stopShimmer()
            visibility = if (state) View.VISIBLE else View.GONE
        }
    }
}