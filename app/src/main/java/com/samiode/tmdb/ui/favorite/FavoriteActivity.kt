package com.samiode.tmdb.ui.favorite

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.samiode.tmdb.R
import com.samiode.tmdb.adapter.MovieVerticalAdapter
import com.samiode.tmdb.databinding.ActivityFavoriteBinding
import com.samiode.tmdb.domain.model.Movie
import com.samiode.tmdb.ui.detail.DetailActivity
import com.samiode.tmdb.ui.detail.DetailActivity.Companion.EXTRA_DETAIL
import com.samiode.tmdb.utils.AdapterExtension.setClickCallback
import com.samiode.tmdb.utils.ViewBindingUtils.viewBinding
import com.samiode.tmdb.utils.ViewExtension.setVerticalMovieView
import com.samiode.tmdb.utils.ViewExtension.setVisible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteActivity : AppCompatActivity() {
    private val binding: ActivityFavoriteBinding by viewBinding(ActivityFavoriteBinding::inflate)
    private val favoriteViewModel: FavoriteViewModel by viewModels()
    private val adapter = MovieVerticalAdapter()
    private val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == Activity.RESULT_OK) loadFavoriteMovies()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            title = getString(R.string.favorite)
            setDisplayShowHomeEnabled(true)
            setDisplayHomeAsUpEnabled(true)
        }

        setup()
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    private fun setup() {
        adapter.setClickCallback { goToDetail(it) }
        binding.rvFavorite.setVerticalMovieView(adapter)

        loadFavoriteMovies()
    }

    private fun loadFavoriteMovies() {
        favoriteViewModel.getFavoriteMovies().observe(this) { movies ->
            if (movies.isNotEmpty()) {
                adapter.submitList(movies)
                showNoDataMessage(false)
            } else showNoDataMessage(true)
        }
    }

    private fun showNoDataMessage(state: Boolean) {
        binding.apply {
            rvFavorite.setVisible(!state)
            tvErrorNoData.setVisible(state)
        }
    }

    private fun goToDetail(movie: Movie) {
        launcher.launch(
            Intent(this, DetailActivity::class.java).putExtra(EXTRA_DETAIL, movie)
        )
    }
}