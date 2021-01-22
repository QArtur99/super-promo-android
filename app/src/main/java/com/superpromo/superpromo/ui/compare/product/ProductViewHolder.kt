package com.superpromo.superpromo.ui.compare.product

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.superpromo.superpromo.GlideRequests
import com.superpromo.superpromo.R
import com.superpromo.superpromo.data.network.model.Product
import com.superpromo.superpromo.ui.util.FormatPrice

class ProductViewHolder constructor(
    private val view: View,
    private val glide: GlideRequests,
    private val clickListener: ComparePagingAdapter.OnClickListener
) : RecyclerView.ViewHolder(view) {

    companion object {
        fun create(
            parent: ViewGroup,
            glide: GlideRequests,
            clickListener: ComparePagingAdapter.OnClickListener
        ): ProductViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_product, parent, false)
            return ProductViewHolder(view, glide, clickListener)
        }
    }

    private val productImg: ImageView = view.findViewById(R.id.productImg)
    private val shopName: TextView = view.findViewById(R.id.shopName)
    private val name: TextView = view.findViewById(R.id.name)
    private val subtitle: TextView = view.findViewById(R.id.subtitle)
    private val price: TextView = view.findViewById(R.id.price)
    private val amount: TextView = view.findViewById(R.id.amount)

    //        private val details: TextView = view.findViewById(R.id.details)
    private val promoInfo: TextView = view.findViewById(R.id.promoInfo)
    private val promo: TextView = view.findViewById(R.id.promo)
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
//            shopName.text = item?.shopId.toString()
        shopName.text = "Żabka"
        name.text = item?.name
        subtitle.text = item?.subtitle
        price.text = priceString
        amount.text = item?.amount
//            details.text = item?.details
        promoInfo.text = item?.promoInfo
        promo.text = item?.promo
    }

    private fun bingImg(item: Product?) {
        if (item?.imgUrl?.startsWith("http") == true) {
            glide.load(item.imgUrl)
                .centerCrop()
                .placeholder(R.drawable.ic_baseline_browser_not_supported_24)
                .into(productImg)
        } else {
            productImg.setImageResource(R.drawable.ic_baseline_browser_not_supported_24)
        }
    }
}