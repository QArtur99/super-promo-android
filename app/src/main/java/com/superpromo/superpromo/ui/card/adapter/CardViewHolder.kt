package com.superpromo.superpromo.ui.card.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.superpromo.superpromo.GlideRequests
import com.superpromo.superpromo.R
import com.superpromo.superpromo.ui.data.CardModel

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

    //    private val shopLogo: ImageView = view.findViewById(R.id.productImg)
    private val cardName: TextView = view.findViewById(R.id.cardName)
    private var cardModel: CardModel? = null

    init {
        view.setOnClickListener {
            cardModel?.let { clickListener.onClick(view, it) }
        }
    }

    fun bind(item: CardModel) {
        cardModel = item
        cardName.text = item.title
//        shopLogo.setImageResource(item.icon)
    }
}