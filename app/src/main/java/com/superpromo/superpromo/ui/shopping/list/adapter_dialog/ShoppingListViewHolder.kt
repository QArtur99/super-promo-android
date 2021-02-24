package com.superpromo.superpromo.ui.shopping.list.adapter_dialog

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.superpromo.superpromo.GlideRequests
import com.superpromo.superpromo.R
import com.superpromo.superpromo.data.db.model.ShoppingListDb


class ShoppingListViewHolder constructor(
    private val view: View,
    private val glide: GlideRequests,
    private val clickListener: ShoppingListListAdapter.OnClickListener
) : RecyclerView.ViewHolder(view) {

    companion object {
        fun create(
            parent: ViewGroup,
            glide: GlideRequests,
            clickListener: ShoppingListListAdapter.OnClickListener
        ): ShoppingListViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_text, parent, false)
            return ShoppingListViewHolder(view, glide, clickListener)
        }
    }

    private val name: TextView = view.findViewById(R.id.text)
    private var shoppingListDb: ShoppingListDb? = null

    init {
        view.setOnClickListener {
            shoppingListDb?.let { clickListener.onClick(view, it) }
        }
    }

    fun bind(item: ShoppingListDb) {
        shoppingListDb = item
        name.text = item.name
    }
}