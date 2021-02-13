package com.superpromo.superpromo.ui.compare.adapter.fromMain

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.superpromo.superpromo.GlideRequests
import com.superpromo.superpromo.R
import com.superpromo.superpromo.data.network.model.Product
import com.superpromo.superpromo.ui.compare.adapter.fromOffer.ProductFromOfferPagingAdapter
import com.superpromo.superpromo.ui.util.FormatPrice
import com.superpromo.superpromo.ui.util.GlideHelper

class ProductImgFromMainVh constructor(
    private val view: View,
    private val glide: GlideRequests,
    private val clickListener: ProductFromMainPagingAdapter.OnClickListener
) : RecyclerView.ViewHolder(view) {

    companion object {
        fun create(
            parent: ViewGroup,
            glide: GlideRequests,
            clickListener: ProductFromMainPagingAdapter.OnClickListener
        ): ProductImgFromMainVh {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_product_img, parent, false)
            return ProductImgFromMainVh(view, glide, clickListener)
        }
    }

    private val productImg: ImageView = view.findViewById(R.id.productImg)
    private val shopName: TextView = view.findViewById(R.id.shopName)
    private var product: Product? = null

    init {
        view.setOnClickListener {
            product?.let { clickListener.onClick(view, it) }
        }
    }

    fun bind(item: Product?) {
        product = item
        item?.imgUrl?.let {
            GlideHelper.bingImg(productImg, glide, it)
        }
        shopName.text = item?.shopName
    }
}