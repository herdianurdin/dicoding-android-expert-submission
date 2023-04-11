package com.samiode.tmdb.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.samiode.tmdb.databinding.CardMovieHorizontalBinding
import com.samiode.tmdb.domain.model.Movie
import com.samiode.tmdb.utils.StringUtils.getPosterImageUrl
import com.samiode.tmdb.utils.ViewExtension.parseMovieRating
import com.samiode.tmdb.utils.ViewExtension.setImageFromUrl

class MovieHorizontalAdapter: ListAdapter<Movie, MovieHorizontalAdapter.ListViewHolder>(DiffUtilCallback) {
    private lateinit var onItemClickCallback: OnItemClickCallback

    inner class ListViewHolder(private var binding: CardMovieHorizontalBinding):
    RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Movie) {
            val rating = movie.voteAverage.div(2)
            binding.apply {
                root.setOnClickListener { onItemClickCallback.onItemClickCallback(movie) }
                tvTitle.text = movie.title
                tvRating.text = String.format("%.2f", rating)
                ivPoster.setImageFromUrl(getPosterImageUrl(movie.posterPath))
                ivRating.parseMovieRating(rating.toInt())
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = CardMovieHorizontalBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val movie = getItem(position)
        holder.bind(movie)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClickCallback(movie: Movie)
    }

    companion object {
        private val DiffUtilCallback = object: DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean =
                oldItem == newItem
        }
    }
}