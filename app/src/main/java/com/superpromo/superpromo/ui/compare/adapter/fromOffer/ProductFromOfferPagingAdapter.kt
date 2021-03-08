package com.superpromo.superpromo.ui.compare.adapter.fromOffer

import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.superpromo.superpromo.GlideRequests
import com.superpromo.superpromo.R
import com.superpromo.superpromo.data.network.model.Product

class ProductFromOfferPagingAdapter(
    private val glide: GlideRequests,
    private val clickListener: OnClickListener,
) : PagingDataAdapter<Product, RecyclerView.ViewHolder>(GridViewDiffCallback) {

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            R.layout.item_product -> (holder as ProductFromOfferVh).bind(getItem(position))
            R.layout.item_product_img -> (holder as ProductImgOfferMainVh).bind(getItem(position))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            R.layout.item_product -> ProductFromOfferVh.create(parent, glide, clickListener)
            R.layout.item_product_img -> ProductImgOfferMainVh.create(parent, glide, clickListener)
            else -> throw IllegalArgumentException("unknown view type $viewType")
        }
    }

    override fun getItemViewType(position: Int): Int {
        val item = getItem(position)!!
        val isOnlyImg = item.isOnlyImg ?: false
        return if (isOnlyImg) R.layout.item_product_img else R.layout.item_product
    }

    companion object GridViewDiffCallback : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }
    }

    interface OnClickListener {
        fun onClick(v: View, product: Product)
        fun onLongClick(v: View, product: Product)
    }

    fun getItemAt(position: Int): Product {
        return getItem(position)!!
    }
}
