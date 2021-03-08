package com.superpromo.superpromo.ui.card.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.superpromo.superpromo.GlideRequests
import com.superpromo.superpromo.R
import com.superpromo.superpromo.data.db.model.CardDb

class CardListAdapter(
    private val glide: GlideRequests,
    private val clickListener: OnClickListener,
) : ListAdapter<CardDb, RecyclerView.ViewHolder>(GridViewDiffCallback) {

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            R.layout.item_card_add -> (holder as CardAddViewHolder).bind(getItem(position))
            R.layout.item_card -> (holder as CardViewHolder).bind(getItem(position))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            R.layout.item_card_add -> CardAddViewHolder.create(parent, glide, clickListener)
            R.layout.item_card -> CardViewHolder.create(parent, glide, clickListener)
            else -> throw IllegalArgumentException("unknown view type $viewType")
        }
    }

    override fun getItemViewType(position: Int): Int {
        val item = getItem(position)!!
        return when (item.name) {
            "" -> R.layout.item_card_add
            else -> R.layout.item_card
        }
    }

    companion object GridViewDiffCallback : DiffUtil.ItemCallback<CardDb>() {
        override fun areItemsTheSame(oldItem: CardDb, newItem: CardDb): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: CardDb, newItem: CardDb): Boolean {
            return oldItem == newItem
        }
    }

    open class OnClickListener(val clickListener: (v: View, cardDb: CardDb) -> Unit) {
        fun onClick(v: View, cardDb: CardDb) = clickListener(v, cardDb)
    }
}
