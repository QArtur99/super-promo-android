package com.superpromo.superpromo.ui.card_add.color_adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.superpromo.superpromo.GlideRequests
import com.superpromo.superpromo.R
import com.superpromo.superpromo.ui.data.CardColorModel

class CardColorViewHolder constructor(
    private val view: View,
    private val glide: GlideRequests,
    private val clickListener: CardColorListAdapter.OnClickListener
) : RecyclerView.ViewHolder(view) {

    companion object {
        fun create(
            parent: ViewGroup,
            glide: GlideRequests,
            clickListener: CardColorListAdapter.OnClickListener
        ): CardColorViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_card_color, parent, false)
            return CardColorViewHolder(view, glide, clickListener)
        }
    }

    private val colorView: CardView = view.findViewById(R.id.color)
    private var cardColorModel: CardColorModel? = null

    init {
        view.setOnClickListener {
            cardColorModel?.let { clickListener.onClick(view, it) }
        }
    }

    fun bind(item: CardColorModel) {
        cardColorModel = item
        colorView.setCardBackgroundColor(view.context.getColor(item.color))
        if (item.click) view.performClick()
    }
}
