package com.samiode.core.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.samiode.core.R
import com.samiode.core.databinding.CardMovieVerticalBinding
import com.samiode.core.domain.model.Movie
import com.samiode.core.utils.StringUtils.getPosterImageUrl
import com.samiode.core.utils.ViewExtension.parseMovieRating
import com.samiode.core.utils.ViewExtension.setImageFromUrl

class MovieVerticalAdapter: ListAdapter<Movie, MovieVerticalAdapter.ListViewHolder>(DiffUtilCallback) {
    private lateinit var onItemClickCallback: OnItemClickCallback

    inner class ListViewHolder(private var binding: CardMovieVerticalBinding):
    RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Movie, context: Context) {
            val rating = movie.voteAverage.div(2)
            binding.apply {
                root.setOnClickListener { onItemClickCallback.onItemClickCallback(movie) }
                tvTitle.text = movie.title
                tvRating.text = String.format("%.2f", rating)
                tvOverview.text = movie.overview.ifEmpty { context.getString(R.string.overview_empty) }
                ivPoster.setImageFromUrl(getPosterImageUrl(movie.posterPath))
                ivRating.parseMovieRating(rating.toInt())
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = CardMovieVerticalBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val movie = getItem(position)
        holder.bind(movie, holder.itemView.context)
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