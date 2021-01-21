package com.superpromo.superpromo.ui.offer

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.superpromo.superpromo.GlideRequests
import com.superpromo.superpromo.R
import com.superpromo.superpromo.data.network.model.Shop

class ShopViewHolder constructor(
    private val view: View,
    private val glide: GlideRequests,
    private val clickListener: ShopListAdapter.OnClickListener
) : RecyclerView.ViewHolder(view) {

    companion object {
        fun create(
            parent: ViewGroup,
            glide: GlideRequests,
            clickListener: ShopListAdapter.OnClickListener
        ): ShopViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_shop, parent, false)
            return ShopViewHolder(view, glide, clickListener)
        }
    }

    private val shopLogo: ImageView = view.findViewById(R.id.productImg)
    private val shopName: TextView = view.findViewById(R.id.shopName)
    private var shop: Shop? = null

    init {
        view.setOnClickListener {
            shop?.let { clickListener.onClick(view, it) }
        }
    }

    fun bind(item: Shop) {
        shop = item
        shopName.text = item.name
        bingImg(item)
    }

    private fun bingImg(item: Shop?) {
        if (item?.imgUrl?.startsWith("http") == true) {
            glide.load(item.imgUrl)
                .centerCrop()
                .placeholder(R.drawable.ic_baseline_browser_not_supported_24)
                .into(shopLogo)
        } else {
            shopLogo.setImageResource(R.drawable.ic_baseline_browser_not_supported_24)
        }
    }
}