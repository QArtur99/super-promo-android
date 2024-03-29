package com.superpromo.superpromo.ui.card.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.superpromo.superpromo.GlideRequests
import com.superpromo.superpromo.R
import com.superpromo.superpromo.data.db.model.CardDb

class CardViewHolder constructor(
    private val view: View,
    private val glide: GlideRequests,
    private val clickListener: CardListAdapter.OnClickListener
) : RecyclerView.ViewHolder(view) {

    companion object {
        fun create(
            parent: ViewGroup,
            glide: GlideRequests,
            clickListener: CardListAdapter.OnClickListener
        ): CardViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_card, parent, false)
            return CardViewHolder(view, glide, clickListener)
        }
    }

    private val background: RelativeLayout = view.findViewById(R.id.background)
    private val cardName: TextView = view.findViewById(R.id.editText)
    private var cardDb: CardDb? = null

    init {
        view.setOnClickListener {
            cardDb?.let { clickListener.onClick(view, it) }
        }
    }

    fun bind(item: CardDb) {
        cardDb = item
        background.setBackgroundColor(Color.parseColor(item.color))
        cardName.text = item.name
    }
}
