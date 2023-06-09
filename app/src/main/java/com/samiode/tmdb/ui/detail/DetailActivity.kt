package com.samiode.tmdb.ui.detail

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.samiode.tmdb.R
import com.samiode.core.adapter.CastAdapter
import com.samiode.core.adapter.MovieHorizontalAdapter
import com.samiode.tmdb.databinding.ActivityDetailBinding
import com.samiode.core.domain.model.Movie
import com.samiode.core.utils.AdapterExtension.setClickCallback
import com.samiode.core.utils.StringUtils.getBackdropImageUrl
import com.samiode.core.utils.StringUtils.getPosterImageUrl
import com.samiode.core.utils.ViewBindingUtils.viewBinding
import com.samiode.core.utils.ViewExtension.parseMovieRating
import com.samiode.core.utils.ViewExtension.setAvailability
import com.samiode.core.utils.ViewExtension.setCastMovieView
import com.samiode.core.utils.ViewExtension.setHorizontalMovieView
import com.samiode.core.utils.ViewExtension.setImageFromUrl
import com.samiode.core.utils.ViewExtension.setVisible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {
    private val binding by viewBinding(ActivityDetailBinding::inflate)
    private val detailViewModel: DetailViewModel by viewModels()

    private val castAdapter = CastAdapter()
    private val recommendationMovieAdapter = MovieHorizontalAdapter()

    private lateinit var movie: Movie
    private var isFavorite: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
            setDisplayShowTitleEnabled(false)
        }

        setup()
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    private fun setup() {
        movie = intent.parcelable(EXTRA_DETAIL)!!

        detailViewModel.isFavoriteMovie(movie.id).observe(this) { isFavorite ->
            this.isFavorite = isFavorite
            toggleFavorite(this.isFavorite)
        }

        val rating = movie.voteAverage.div(2)

        binding.apply {
            ivBackdrop.setImageFromUrl(getBackdropImageUrl(movie.posterPath))
            ivPoster.setImageFromUrl(getPosterImageUrl(movie.posterPath))
            tvTitle.text = movie.title
            tvRating.text = String.format("%.2f", rating)
            ivRating.parseMovieRating(rating.toInt())
            tvOverview.text = movie.overview.ifEmpty { getString(R.string.overview_empty) }
            fabFavorite.setOnClickListener { handleFavoriteMovie() }
        }

        setCastMovie()
        setRelatedMovies()
    }

    private fun goToDetail(movie: Movie) {
        Intent(this, DetailActivity::class.java).also {
            it.putExtra(EXTRA_DETAIL, movie)
            startActivity(it)
        }
    }

    private fun setCastMovie() {
        binding.rvCast.setCastMovieView(castAdapter)
        detailViewModel.getMovieCasts(movie.id).observe(this) { result ->
            result.onSuccess { casts ->
                binding.sflCast.setAvailability(false)

                if (casts.isNotEmpty()) castAdapter.submitList(casts.take(4))
                else binding.clCast.setVisible(false)
            }
        }
    }

    private fun setRelatedMovies() {
        recommendationMovieAdapter.setClickCallback { goToDetail(it) }
        binding.rvRecommendationMovies.setHorizontalMovieView(recommendationMovieAdapter)

        detailViewModel.getRecommendationMovies(movie.id).observe(this) { result ->
            result.onSuccess { movies ->
                binding.sflRecommendationMovies.setAvailability(false)

                if (movies.isNotEmpty()) recommendationMovieAdapter.submitList(movies)
                else binding.clRecommendationMovies.setVisible(false)
            }
            result.onFailure { showErrorOccurred() }
        }
    }

    private fun handleFavoriteMovie() {
        isFavorite = if (isFavorite) {
            detailViewModel.removeMovieFromFavorite(movie.id)
            false
        } else {
            detailViewModel.putMovieAsFavorite(movie)
            true
        }
        toggleFavorite(isFavorite)
        setResult(Activity.RESULT_OK)
    }

    private fun toggleFavorite(state: Boolean) {
        binding.fabFavorite.setImageResource(
            if (state) R.drawable.ic_favorite
            else R.drawable.ic_favorite_border
        )
    }

    private inline fun <reified T : Parcelable> Intent.parcelable(key: String): T? = when {
        Build.VERSION.SDK_INT >= 33 -> getParcelableExtra(key, T::class.java)
        else -> @Suppress("DEPRECATION") getParcelableExtra(key) as? T
    }

    private fun showErrorOccurred() {
        Toast.makeText(this, getString(R.string.toast_error_occurred), Toast.LENGTH_SHORT).show()
    }

    companion object {
        const val EXTRA_DETAIL = "extra_detail"
    }
}