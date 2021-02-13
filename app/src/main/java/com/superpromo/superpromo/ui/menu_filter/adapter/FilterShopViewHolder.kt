package com.superpromo.superpromo.ui.menu_filter.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.request.RequestOptions
import com.superpromo.superpromo.GlideRequests
import com.superpromo.superpromo.R
import com.superpromo.superpromo.data.network.model.Shop
import com.superpromo.superpromo.ui.util.GlideHelper
import com.superpromo.superpromo.ui.util.Url

class FilterShopViewHolder constructor(
    private val view: View,
    private val glide: GlideRequests,
    private val clickListener: FilterShopListAdapter.OnClickListener
) : RecyclerView.ViewHolder(view) {

    companion object {
        fun create(
            parent: ViewGroup,
            glide: GlideRequests,
            clickListener: FilterShopListAdapter.OnClickListener
        ): FilterShopViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_shop_filter, parent, false)
            return FilterShopViewHolder(view, glide, clickListener)
        }
    }

    private val shopLogo: ImageView = view.findViewById(R.id.productImg)
    private val shopName: TextView = view.findViewById(R.id.shopName)
    private val checkbox: CheckBox = view.findViewById(R.id.checkbox)
    private var shop: Shop? = null

    init {
        view.setOnClickListener {
            shop?.let { clickListener.onClick(view, it) }
        }
    }

    fun bind(item: Shop) {
        shop = item
        shopName.text = item.name
        checkbox.isChecked = item.isAvailableInDb
        item.imgUrl?.let {
            val imgUrl = Url.getBaseUrl() + it
            GlideHelper.bingImg(shopLogo, glide, imgUrl)
        }
    }
}