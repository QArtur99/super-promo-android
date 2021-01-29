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

class ProductFromMainVh constructor(
    private val view: View,
    private val glide: GlideRequests,
    private val clickListener: ProductFromMainPagingAdapter.OnClickListener
) : RecyclerView.ViewHolder(view) {

    companion object {
        fun create(
            parent: ViewGroup,
            glide: GlideRequests,
            clickListener: ProductFromMainPagingAdapter.OnClickListener
        ): ProductFromMainVh {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_product, parent, false)
            return ProductFromMainVh(view, glide, clickListener)
        }
    }

    private val productImg: ImageView = view.findViewById(R.id.productImg)
    private val shopName: TextView = view.findViewById(R.id.shopName)
    private val name: TextView = view.findViewById(R.id.name)
    private val price: TextView = view.findViewById(R.id.price)
    private val amount: TextView = view.findViewById(R.id.amount)
    private val details: TextView = view.findViewById(R.id.details)
    private var product: Product? = null

    init {
        view.setOnClickListener {
            product?.let { clickListener.onClick(view, it) }
        }
    }

    fun bind(item: Product?) {
        product = item
        bingImg(item)
        val priceString = FormatPrice.getCurrency(item?.price, "zł")
        shopName.text = item?.shopName
        name.text = item?.name
        price.text = priceString
        if (item?.amount.isNullOrEmpty()) {
            amount.visibility = View.GONE
        } else {
            amount.text = item?.amount
        }
        if (item?.details.isNullOrEmpty()) {
            details.visibility = View.GONE
        } else {
            details.text = item?.details
        }
    }

    private fun bingImg(item: Product?) {
        if (item?.imgUrl?.startsWith("http") == true) {
            glide.load(item.imgUrl)
                .fitCenter()
                .placeholder(R.drawable.loading_animation)
                .error(R.drawable.ic_baseline_broken_image_24)
                .into(productImg)
        } else {
            productImg.setImageResource(R.drawable.ic_baseline_broken_image_24)
        }
    }
}