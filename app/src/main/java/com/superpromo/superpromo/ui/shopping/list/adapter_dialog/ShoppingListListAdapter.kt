package com.superpromo.superpromo.ui.shopping.list.adapter_dialog

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.superpromo.superpromo.GlideRequests
import com.superpromo.superpromo.R
import com.superpromo.superpromo.data.db.model.ShoppingListDb

class ShoppingListListAdapter(
    private val glide: GlideRequests,
    private val clickListener: OnClickListener,
) : ListAdapter<ShoppingListDb, RecyclerView.ViewHolder>(GridViewDiffCallback) {

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            R.layout.item_text -> (holder as ShoppingListViewHolder).bind(getItem(position))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            R.layout.item_text -> ShoppingListViewHolder.create(parent, glide, clickListener)
            else -> throw IllegalArgumentException("unknown view type $viewType")
        }
    }

    override fun getItemViewType(position: Int): Int {
        val item = getItem(position)!!
        return R.layout.item_text
    }

    companion object GridViewDiffCallback : DiffUtil.ItemCallback<ShoppingListDb>() {
        override fun areItemsTheSame(oldItem: ShoppingListDb, newItem: ShoppingListDb): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ShoppingListDb, newItem: ShoppingListDb): Boolean {
            return oldItem == newItem
        }
    }

    open class OnClickListener(val clickListener: (v: View, shoppingListDb: ShoppingListDb) -> Unit) {
        fun onClick(v: View, shoppingListDb: ShoppingListDb) = clickListener(v, shoppingListDb)
    }
}
