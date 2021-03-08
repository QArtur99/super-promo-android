package com.superpromo.superpromo.ui.menu_left.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.superpromo.superpromo.GlideRequests
import com.superpromo.superpromo.R
import com.superpromo.superpromo.ui.data.MenuModel

class MenuListAdapter(
    private val glide: GlideRequests,
    private val clickListener: OnClickListener,
) : ListAdapter<MenuModel, RecyclerView.ViewHolder>(GridViewDiffCallback) {

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            R.layout.item_menu -> (holder as MenuViewHolder).bind(getItem(position))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            R.layout.item_menu -> MenuViewHolder.create(parent, glide, clickListener)
            else -> throw IllegalArgumentException("unknown view type $viewType")
        }
    }

    override fun getItemViewType(position: Int): Int {
        val item = getItem(position)!!
        return R.layout.item_menu
    }

    companion object GridViewDiffCallback : DiffUtil.ItemCallback<MenuModel>() {
        override fun areItemsTheSame(oldItem: MenuModel, newItem: MenuModel): Boolean {
            return oldItem.title == newItem.title
        }

        override fun areContentsTheSame(oldItem: MenuModel, newItem: MenuModel): Boolean {
            return oldItem == newItem
        }
    }

    open class OnClickListener(val clickListener: (v: View, MenuModel: MenuModel) -> Unit) {
        fun onClick(v: View, MenuModel: MenuModel) = clickListener(v, MenuModel)
    }
}
