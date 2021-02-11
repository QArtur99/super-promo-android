package com.superpromo.superpromo.ui.menu_left.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.superpromo.superpromo.GlideRequests
import com.superpromo.superpromo.R
import com.superpromo.superpromo.data.network.model.Shop
import com.superpromo.superpromo.ui.data.MenuModel
import com.superpromo.superpromo.ui.offer.adapter.ShopViewHolder

class MenuViewHolder constructor(
    private val view: View,
    private val glide: GlideRequests,
    private val clickListener: MenuListAdapter.OnClickListener
) : RecyclerView.ViewHolder(view) {

    companion object {
        fun create(
            parent: ViewGroup,
            glide: GlideRequests,
            clickListener: MenuListAdapter.OnClickListener
        ): MenuViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_menu, parent, false)
            return MenuViewHolder(view, glide, clickListener)
        }
    }

    private val shopLogo: ImageView = view.findViewById(R.id.productImg)
    private val shopName: TextView = view.findViewById(R.id.shopName)
    private var menuModel: MenuModel? = null

    init {
        view.setOnClickListener {
            menuModel?.let { clickListener.onClick(view, it) }
        }
    }

    fun bind(item: MenuModel) {
        menuModel = item
        shopLogo.setImageResource(item.icon)
        shopName.text = view.context.getText(item.title)
    }
}