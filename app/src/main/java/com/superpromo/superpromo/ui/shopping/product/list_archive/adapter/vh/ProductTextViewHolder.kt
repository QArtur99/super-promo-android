package com.superpromo.superpromo.ui.shopping.product.list_archive.adapter.vh

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.superpromo.superpromo.GlideRequests
import com.superpromo.superpromo.R
import com.superpromo.superpromo.data.db.model.ProductDb
import com.superpromo.superpromo.ui.shopping.product.list_archive.adapter.ProductArchiveListAdapter
import com.superpromo.superpromo.ui.util.FormatPrice
import com.superpromo.superpromo.ui.util.GlideHelper

class ProductTextViewHolder constructor(
    private val view: View,
    private val glide: GlideRequests,
    private val clickListener: ProductArchiveListAdapter.OnClickListener
) : RecyclerView.ViewHolder(view) {

    companion object {
        fun create(
            parent: ViewGroup,
            glide: GlideRequests,
            clickListener: ProductArchiveListAdapter.OnClickListener
        ): ProductTextViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_shopping_product_text, parent, false)
            return ProductTextViewHolder(view, glide, clickListener)
        }
    }

    private val checkbox: CheckBox = view.findViewById(R.id.checkbox)
    private val productImg: ImageView = view.findViewById(R.id.productImg)
    private val shopName: TextView = view.findViewById(R.id.shopName)
    private val name: TextView = view.findViewById(R.id.name)
    private val price: TextView = view.findViewById(R.id.price)
    private val amount: TextView = view.findViewById(R.id.amount)
    private val details: TextView = view.findViewById(R.id.details)
    private var product: ProductDb? = null

    init {
        view.setOnClickListener {
            product?.let { clickListener.onClick(view, it) }
        }
        checkbox.setOnClickListener {
            product?.let { clickListener.onSelect(view, it) }
        }
    }

    fun bind(item: ProductDb) {
        product = item
        item.imgUrl?.let {
            GlideHelper.bingImg(productImg, glide, it)
        }
        val priceString = FormatPrice.getCurrency(item.price, "zł")
        shopName.text = item.shopName
        name.text = item.name
        price.text = priceString
        if (item.price == null) {
            price.visibility = View.GONE
        } else {
            price.text = item.amount
        }
        if (item.amount.isNullOrEmpty()) {
            amount.visibility = View.GONE
        } else {
            amount.text = item.amount
        }
        if (item.details.isNullOrEmpty()) {
            details.visibility = View.GONE
        } else {
            details.text = item.details
        }
        checkbox.isChecked = item.isSelected
        checkbox.buttonTintList = ColorStateList.valueOf(view.context.getColor(R.color.grey_light))
        checkbox.isEnabled = false
    }
}
