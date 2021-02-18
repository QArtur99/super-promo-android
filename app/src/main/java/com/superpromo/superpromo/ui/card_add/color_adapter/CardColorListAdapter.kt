package com.superpromo.superpromo.ui.card_add.color_adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.superpromo.superpromo.GlideRequests
import com.superpromo.superpromo.R
import com.superpromo.superpromo.ui.data.CardColorModel


class CardColorListAdapter(
    private val glide: GlideRequests,
    private val clickListener: OnClickListener,
) : ListAdapter<CardColorModel, RecyclerView.ViewHolder>(GridViewDiffCallback) {

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            R.layout.item_card_color -> (holder as CardColorViewHolder).bind(getItem(position))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            R.layout.item_card_color -> CardColorViewHolder.create(parent, glide, clickListener)
            else -> throw IllegalArgumentException("unknown view type $viewType")
        }
    }

    override fun getItemViewType(position: Int): Int {
        val item = getItem(position)!!
        return R.layout.item_card_color
    }

    companion object GridViewDiffCallback : DiffUtil.ItemCallback<CardColorModel>() {
        override fun areItemsTheSame(oldItem: CardColorModel, newItem: CardColorModel): Boolean {
            return oldItem.color == newItem.color
        }

        override fun areContentsTheSame(oldItem: CardColorModel, newItem: CardColorModel): Boolean {
            return oldItem == newItem
        }
    }

    open class OnClickListener(val clickListener: (v: View, cardColorModel: CardColorModel) -> Unit) {
        fun onClick(v: View, cardColorModel: CardColorModel) = clickListener(v, cardColorModel)
    }
}