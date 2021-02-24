package com.superpromo.superpromo.ui.compare.adapter.fromOffer

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.superpromo.superpromo.GlideRequests
import com.superpromo.superpromo.R
import com.superpromo.superpromo.data.network.model.Product
import com.superpromo.superpromo.ui.util.GlideHelper

class ProductImgOfferMainVh constructor(
    private val view: View,
    private val glide: GlideRequests,
    private val clickListener: ProductFromOfferPagingAdapter.OnClickListener
) : RecyclerView.ViewHolder(view) {

    companion object {
        fun create(
            parent: ViewGroup,
            glide: GlideRequests,
            clickListener: ProductFromOfferPagingAdapter.OnClickListener
        ): ProductImgOfferMainVh {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_product_img, parent, false)
            return ProductImgOfferMainVh(view, glide, clickListener)
        }
    }

    private val productImg: ImageView = view.findViewById(R.id.productImg)
    private val shopName: TextView = view.findViewById(R.id.shopName)
    private var product: Product? = null

    init {
        view.setOnClickListener {
            product?.let { clickListener.onClick(view, it) }
        }
        view.setOnLongClickListener {
            product?.let { clickListener.onLongClick(view, it) }
            true
        }
    }

    fun bind(item: Product?) {
        product = item
        item?.imgUrl?.let {
            GlideHelper.bingImg(productImg, glide, it)
        }
        shopName.text = item?.shopName
        shopName.visibility = View.GONE
    }
}