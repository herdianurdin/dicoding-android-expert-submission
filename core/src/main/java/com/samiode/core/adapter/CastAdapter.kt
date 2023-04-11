package com.samiode.core.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.samiode.core.databinding.CardCastBinding
import com.samiode.core.domain.model.Cast
import com.samiode.core.utils.StringUtils.getProfileImageUrl
import com.samiode.core.utils.ViewExtension.setImageFromUrl

class CastAdapter: ListAdapter<Cast, CastAdapter.ListViewHolder>(DiffUtilCallback) {
    inner class ListViewHolder(private var binding: CardCastBinding):
    RecyclerView.ViewHolder(binding.root) {
        fun bind(cast: Cast) {
            binding.apply {
                tvCharacter.text = cast.character
                tvName.text = cast.name
                ivProfile.setImageFromUrl(getProfileImageUrl(cast.profilePath), true)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = CardCastBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val cast = getItem(position)
        holder.bind(cast)
    }

    companion object {
        private val DiffUtilCallback = object: DiffUtil.ItemCallback<Cast>() {
            override fun areItemsTheSame(oldItem: Cast, newItem: Cast): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Cast, newItem: Cast): Boolean =
                oldItem == newItem
        }
    }
}