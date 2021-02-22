package com.superpromo.superpromo.ui.shopping.product.list_active.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.superpromo.superpromo.GlideRequests
import com.superpromo.superpromo.R
import com.superpromo.superpromo.data.db.model.ProductDb
import com.superpromo.superpromo.ui.shopping.product.list_active.adapter.vh.ProductImgViewHolder
import com.superpromo.superpromo.ui.shopping.product.list_active.adapter.vh.ProductTextViewHolder
import com.superpromo.superpromo.ui.shopping.product.list_active.adapter.vh.ProductViewHolder


class ProductListAdapter(
    private val glide: GlideRequests,
    private val clickListener: OnClickListener,
) : ListAdapter<ProductDb, RecyclerView.ViewHolder>(GridViewDiffCallback) {

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            R.layout.item_shopping_product -> (holder as ProductViewHolder)
                .bind(getItem(position))
            R.layout.item_shopping_product_img -> (holder as ProductImgViewHolder)
                .bind(getItem(position))
            R.layout.item_shopping_product_text -> (holder as ProductTextViewHolder)
                .bind(getItem(position))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            R.layout.item_shopping_product -> ProductViewHolder
                .create(parent, glide, clickListener)
            R.layout.item_shopping_product_img -> ProductImgViewHolder
                .create(parent, glide, clickListener)
            R.layout.item_shopping_product_text -> ProductTextViewHolder
                .create(parent, glide, clickListener)
            else -> throw IllegalArgumentException("unknown view type $viewType")
        }
    }

    override fun getItemViewType(position: Int): Int {
        val item = getItem(position)!!
        val isLocal = item.isLocal ?: false
        val isOnlyImg = item.isOnlyImg ?: false
        return when {
            isLocal -> R.layout.item_shopping_product_text
            isOnlyImg -> R.layout.item_shopping_product_img
            else -> R.layout.item_shopping_product
        }
    }

    companion object GridViewDiffCallback : DiffUtil.ItemCallback<ProductDb>() {
        override fun areItemsTheSame(oldItem: ProductDb, newItem: ProductDb): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ProductDb, newItem: ProductDb): Boolean {
            return oldItem == newItem
        }
    }

    interface OnClickListener {
        fun onClick(v: View, productDb: ProductDb)
        fun onSelect(v: View, productDb: ProductDb)
    }
}