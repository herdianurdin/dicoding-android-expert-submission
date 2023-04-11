package com.samiode.tmdb.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.ConfigurationCompat
import com.samiode.tmdb.R
import com.samiode.tmdb.adapter.MovieHorizontalAdapter
import com.samiode.tmdb.databinding.ActivityMainBinding
import com.samiode.tmdb.domain.model.Movie
import com.samiode.tmdb.ui.category.CategoryActivity
import com.samiode.tmdb.ui.category.CategoryActivity.Companion.CATEGORY_POPULAR
import com.samiode.tmdb.ui.category.CategoryActivity.Companion.CATEGORY_TRENDING
import com.samiode.tmdb.ui.category.CategoryActivity.Companion.CATEGORY_UPCOMING
import com.samiode.tmdb.ui.category.CategoryActivity.Companion.EXTRA_CATEGORY
import com.samiode.tmdb.ui.detail.DetailActivity
import com.samiode.tmdb.ui.detail.DetailActivity.Companion.EXTRA_DETAIL
import com.samiode.tmdb.ui.favorite.FavoriteActivity
import com.samiode.tmdb.ui.search.SearchActivity
import com.samiode.tmdb.utils.AdapterExtension.setClickCallback
import com.samiode.tmdb.utils.StringUtils.getBackdropImageUrl
import com.samiode.tmdb.utils.ViewBindingUtils.viewBinding
import com.samiode.tmdb.utils.ViewExtension.setAvailability
import com.samiode.tmdb.utils.ViewExtension.setHorizontalMovieView
import com.samiode.tmdb.utils.ViewExtension.setImageFromUrl
import com.samiode.tmdb.utils.ViewExtension.setVisible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val binding by viewBinding(ActivityMainBinding::inflate)
    private val mainViewModel: MainViewModel by viewModels()

    private val trendingAdapter = MovieHorizontalAdapter()
    private val popularAdapter = MovieHorizontalAdapter()
    private val upcomingAdapter = MovieHorizontalAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        setup()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean =
        when(item.itemId) {
            R.id.btn_search -> {
                Intent(this, SearchActivity::class.java).also { startActivity(it)}
                true
            }
            R.id.btn_favorite -> {
                Intent(this, FavoriteActivity::class.java).also { startActivity(it) }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    private fun setup() {
        trendingAdapter.setClickCallback { goToDetail(it) }
        popularAdapter.setClickCallback { goToDetail(it) }
        upcomingAdapter.setClickCallback { goToDetail(it) }

        binding.apply {
            rvTrendingMovies.setHorizontalMovieView(trendingAdapter)
            rvPopularMovies.setHorizontalMovieView(popularAdapter)
            rvUpcomingMovies.setHorizontalMovieView(upcomingAdapter)
            btnSeeMoreTrending.setOnClickListener { goToCategory(CATEGORY_TRENDING) }
            btnSeeMorePopular.setOnClickListener { goToCategory(CATEGORY_POPULAR) }
            btnSeeMoreUpcoming.setOnClickListener { goToCategory(CATEGORY_UPCOMING) }
            btnRetry.setOnClickListener { getAllMovies() }
        }

        getAllMovies()
    }

    private fun goToDetail(movie: Movie) {
        Intent(this, DetailActivity::class.java).also {
            it.putExtra(EXTRA_DETAIL, movie)
            startActivity(it)
        }
    }

    private fun goToCategory(category: String) {
        Intent(this, CategoryActivity::class.java).also {
            it.putExtra(EXTRA_CATEGORY, category)
            startActivity(it)
        }
    }

    private fun getAllMovies() {
        val region = ConfigurationCompat.getLocales(resources.configuration)[0]!!.country

        loadTrendingMovies(region)
        loadUpcomingMovies(region)
        loadPopularMovies(region)
    }

    private fun loadTrendingMovies(region: String) {
        mainViewModel.getTrendingMovies(region).observe(this) { result ->
            result.onSuccess {movies ->
                if (movies.isNotEmpty()) {
                    trendingAdapter.submitList(movies.take(5))

                    binding.apply {
                        ivBackdrop.setImageFromUrl(
                            getBackdropImageUrl(movies.shuffled()[0].posterPath)
                        )
                        sflBackdrop.setAvailability(false)
                        sflTrending.setAvailability(false)
                    }
                }

                showErrorOccurred(false)
            }
            result.onFailure { showErrorOccurred(true) }
        }
    }

    private fun loadPopularMovies(region: String) {
        mainViewModel.getPopularMovies(region).observe(this) { result ->
            result.onSuccess { movies ->
                if (movies.isNotEmpty()) {
                    popularAdapter.submitList(movies.take(5))
                    binding.sflPopular.setAvailability(false)
                }

                showErrorOccurred(false)
            }
            result.onFailure { showErrorOccurred(true) }
        }
    }

    private fun loadUpcomingMovies(region: String) {
        mainViewModel.getUpcomingMovies(region).observe(this) { result ->
            result.onSuccess { movies ->
                if (movies.isNotEmpty()) {
                    upcomingAdapter.submitList(movies.take(5))
                    binding.sflUpcoming.setAvailability(false)
                }

                showErrorOccurred(false)
            }
            result.onFailure { showErrorOccurred(true) }
        }
    }

    private fun showErrorOccurred(state: Boolean) {
        binding.svContainer.setVisible(!state)
        binding.clError.setVisible(state)
    }
}