package com.superpromo.superpromo.ui.shopping.product.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.superpromo.superpromo.GlideRequests
import com.superpromo.superpromo.R
import com.superpromo.superpromo.data.db.model.ProductDb
import com.superpromo.superpromo.ui.util.GlideHelper


class ProductImgViewHolder constructor(
    private val view: View,
    private val glide: GlideRequests,
    private val clickListener: ProductListAdapter.OnClickListener
) : RecyclerView.ViewHolder(view) {

    companion object {
        fun create(
            parent: ViewGroup,
            glide: GlideRequests,
            clickListener: ProductListAdapter.OnClickListener
        ): ProductImgViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_product_img, parent, false)
            return ProductImgViewHolder(view, glide, clickListener)
        }
    }

    private val productImg: ImageView = view.findViewById(R.id.productImg)
    private val shopName: TextView = view.findViewById(R.id.shopName)
    private var product: ProductDb? = null

    init {
        view.setOnClickListener {
            product?.let { clickListener.onClick(view, it) }
        }
    }

    fun bind(item: ProductDb) {
        product = item
        item.imgUrl?.let {
            GlideHelper.bingImg(productImg, glide, it)
        }
        shopName.text = item.shopName
    }
}