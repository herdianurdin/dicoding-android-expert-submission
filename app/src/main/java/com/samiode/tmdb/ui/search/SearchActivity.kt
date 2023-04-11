package com.samiode.tmdb.ui.search

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import com.samiode.tmdb.R
import com.samiode.core.adapter.MovieVerticalAdapter
import com.samiode.tmdb.databinding.ActivitySearchBinding
import com.samiode.core.domain.model.Movie
import com.samiode.tmdb.ui.detail.DetailActivity
import com.samiode.tmdb.ui.detail.DetailActivity.Companion.EXTRA_DETAIL
import com.samiode.core.utils.AdapterExtension.setClickCallback
import com.samiode.core.utils.ViewBindingUtils.viewBinding
import com.samiode.core.utils.ViewExtension.setVerticalMovieView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchActivity : AppCompatActivity() {
    private val binding by viewBinding(ActivitySearchBinding::inflate)
    private val searchViewModel: SearchViewModel by viewModels()
    private val adapter = MovieVerticalAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }

        adapter.setClickCallback { goToDetail(it) }
        binding.rvSearch.setVerticalMovieView(adapter)
    }

    override fun onNavigateUp(): Boolean {
        finish()
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.search_menu, menu)

        val searchItem = menu.findItem(R.id.btn_search)
        val searchView = searchItem.actionView as SearchView

        searchItem.expandActionView()
        searchView.queryHint = getString(R.string.search_hint)

        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                searchMovieByQuery(query)
                searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(query: String): Boolean = false
        })

        searchItem.setOnActionExpandListener(object: MenuItem.OnActionExpandListener {
            override fun onMenuItemActionExpand(p0: MenuItem): Boolean = true

            override fun onMenuItemActionCollapse(p0: MenuItem): Boolean {
                finish()
                return false
            }
        })

        return true
    }

    private fun searchMovieByQuery(query: String) {
        searchViewModel.searchMovieByQuery(query).observe(this) { result ->
            result.onSuccess { movies ->
                if (movies.isNotEmpty()) adapter.submitList(movies)
            }
        }
    }

    private fun goToDetail(movie: Movie) {
        Intent(this, DetailActivity::class.java).also {
            it.putExtra(EXTRA_DETAIL, movie)
            startActivity(it)
        }
    }
}