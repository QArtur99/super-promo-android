package com.superpromo.superpromo.ui.shopping.list_archived.adapter

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
    private val clickListener: ShoppingListArchiveListAdapter.OnClickListener
) : RecyclerView.ViewHolder(view) {

    companion object {
        fun create(
            parent: ViewGroup,
            glide: GlideRequests,
            clickListener: ShoppingListArchiveListAdapter.OnClickListener
        ): ShoppingListViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_shopping_list, parent, false)
            return ShoppingListViewHolder(view, glide, clickListener)
        }
    }

    private val name: TextView = view.findViewById(R.id.name)
    private val date: TextView = view.findViewById(R.id.date)
    private var shoppingListDb: ShoppingListDb? = null

    init {
        view.setOnClickListener {
            shoppingListDb?.let { clickListener.onClick(view, it) }
        }
    }

    fun bind(item: ShoppingListDb) {
        shoppingListDb = item
        name.text = item.name
        date.text = item.created.toString()
    }
}