package com.samiode.tmdb.ui.category

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.os.ConfigurationCompat
import com.samiode.tmdb.R
import com.samiode.tmdb.adapter.MovieVerticalAdapter
import com.samiode.tmdb.databinding.ActivityCategoryBinding
import com.samiode.tmdb.domain.model.Movie
import com.samiode.tmdb.ui.detail.DetailActivity
import com.samiode.tmdb.utils.AdapterExtension.setClickCallback
import com.samiode.tmdb.utils.ViewBindingUtils.viewBinding
import com.samiode.tmdb.utils.ViewExtension.setAvailability
import com.samiode.tmdb.utils.ViewExtension.setVerticalMovieView
import com.samiode.tmdb.utils.ViewExtension.setVisible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CategoryActivity : AppCompatActivity() {
    private val binding: ActivityCategoryBinding by viewBinding(ActivityCategoryBinding::inflate)
    private val categoryViewModel: CategoryViewModel by viewModels()

    private val adapter = MovieVerticalAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setup()
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    private fun setup() {
        val category = intent.getStringExtra(EXTRA_CATEGORY)!!
        val region = ConfigurationCompat.getLocales(resources.configuration)[0]!!.country

        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            title = category
            setDisplayShowHomeEnabled(true)
            setDisplayHomeAsUpEnabled(true)
        }

        adapter.setClickCallback { goToDetail(it) }
        binding.rvCategory.setVerticalMovieView(adapter)

        when(category) {
            CATEGORY_TRENDING -> {
                categoryViewModel.getTrendingMovies(region).observe(this) { result ->
                    result.onSuccess { loadRecyclerView(it) }
                    result.onFailure { onErrorOccurred()  }
                }
            }
            CATEGORY_POPULAR -> {
                categoryViewModel.getPopularMovies(region).observe(this) { result ->
                    result.onSuccess { loadRecyclerView(it) }
                    result.onFailure { onErrorOccurred() }
                }
            }
            CATEGORY_UPCOMING -> {
                categoryViewModel.getUpcomingMovies(region).observe(this) { result ->
                    result.onSuccess { loadRecyclerView(it) }
                    result.onFailure { onErrorOccurred() }
                }
            }
            else -> throw IllegalArgumentException("Invalid category argument!")
        }
    }

    private fun loadRecyclerView(movies: List<Movie>) {
        if (movies.isNotEmpty()) {
            adapter.submitList(movies)
        } else binding.apply {
            rvCategory.setVisible(false)
            tvErrorMessage.text = getString(R.string.message_error_no_data)
            tvErrorMessage.setVisible(true)
        }
        binding.sflCategory.setAvailability(false)
    }

    private fun goToDetail(movie: Movie) {
        Intent(this, DetailActivity::class.java).also {
            it.putExtra(DetailActivity.EXTRA_DETAIL, movie)
            startActivity(it)
        }
    }

    private fun onErrorOccurred() {
        binding.apply {
            sflCategory.setAvailability(false)
            rvCategory.setVisible(false)
            tvErrorMessage.text = getString(R.string.message_error_occurred)
            tvErrorMessage.setVisible(true)
        }
    }

    companion object {
        const val EXTRA_CATEGORY = "extra_category"
        const val CATEGORY_TRENDING = "Trending"
        const val CATEGORY_POPULAR = "Popular"
        const val CATEGORY_UPCOMING = "Upcoming"
    }
}