package com.superpromo.superpromo.ui.card.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.superpromo.superpromo.GlideRequests
import com.superpromo.superpromo.R
import com.superpromo.superpromo.data.db.model.CardDb

class CardAddViewHolder constructor(
    private val view: View,
    private val glide: GlideRequests,
    private val clickListener: CardListAdapter.OnClickListener
) : RecyclerView.ViewHolder(view) {

    companion object {
        fun create(
            parent: ViewGroup,
            glide: GlideRequests,
            clickListener: CardListAdapter.OnClickListener
        ): CardAddViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_card_add, parent, false)
            return CardAddViewHolder(view, glide, clickListener)
        }
    }

    private var cardDb: CardDb? = null

    init {
        view.setOnClickListener {
            cardDb?.let { clickListener.onClick(view, it) }
        }
    }

    fun bind(item: CardDb) {
        cardDb = item
    }
}
