package com.superpromo.superpromo.ui.shopping.list.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.superpromo.superpromo.GlideRequests
import com.superpromo.superpromo.R
import com.superpromo.superpromo.data.db.model.ShoppingListDb

class ShoppingListAddViewHolder constructor(
    private val view: View,
    private val glide: GlideRequests,
    private val clickListener: ShoppingListListAdapter.OnClickListener
) : RecyclerView.ViewHolder(view) {

    companion object {
        fun create(
            parent: ViewGroup,
            glide: GlideRequests,
            clickListener: ShoppingListListAdapter.OnClickListener
        ): ShoppingListAddViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_shopping_list_add, parent, false)
            return ShoppingListAddViewHolder(view, glide, clickListener)
        }
    }

    private var shoppingListDb: ShoppingListDb? = null

    init {
        view.setOnClickListener {
            shoppingListDb?.let { clickListener.onClick(view, it) }
        }
    }

    fun bind(item: ShoppingListDb) {
        shoppingListDb = item
    }
}
