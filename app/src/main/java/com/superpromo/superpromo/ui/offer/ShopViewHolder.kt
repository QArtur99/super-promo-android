package com.superpromo.superpromo.ui.offer

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.superpromo.superpromo.GlideRequests
import com.superpromo.superpromo.R
import com.superpromo.superpromo.data.network.model.Product
import com.superpromo.superpromo.data.network.model.Shop
import com.superpromo.superpromo.ui.compare.ProductViewHolder

class ShopViewHolder constructor(
    private val view: View,
    private val glide: GlideRequests
) : RecyclerView.ViewHolder(view) {

    companion object {
        fun create(parent: ViewGroup, glide: GlideRequests): ShopViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_shop, parent, false)
            return ShopViewHolder(view, glide)
        }
    }
    private val shopLogo: ImageView = view.findViewById(R.id.productImg)
    private val shopName: TextView = view.findViewById(R.id.shopName)
    private var shop: Shop? = null

    fun bind(clickListener: ShopListAdapter.OnClickListener, item: Shop) {
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