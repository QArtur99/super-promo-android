package com.superpromo.superpromo.ui.shopping.product.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.superpromo.superpromo.GlideRequests
import com.superpromo.superpromo.R
import com.superpromo.superpromo.data.db.model.ProductDb
import com.superpromo.superpromo.ui.compare.adapter.fromMain.ProductFromMainVh
import com.superpromo.superpromo.ui.compare.adapter.fromMain.ProductImgFromMainVh


class ProductListAdapter(
    private val glide: GlideRequests,
    private val clickListener: OnClickListener,
) : ListAdapter<ProductDb, RecyclerView.ViewHolder>(GridViewDiffCallback) {

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            R.layout.item_product -> (holder as ProductViewHolder).bind(getItem(position))
            R.layout.item_product_img -> (holder as ProductImgViewHolder).bind(getItem(position))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            R.layout.item_product -> ProductViewHolder.create(parent, glide, clickListener)
            R.layout.item_product_img -> ProductImgViewHolder.create(parent, glide, clickListener)
            else -> throw IllegalArgumentException("unknown view type $viewType")
        }
    }

    override fun getItemViewType(position: Int): Int {
        val item = getItem(position)!!
        val isOnlyImg = item.isOnlyImg ?: false
        return if (isOnlyImg) R.layout.item_product_img else R.layout.item_product
    }

    companion object GridViewDiffCallback : DiffUtil.ItemCallback<ProductDb>() {
        override fun areItemsTheSame(oldItem: ProductDb, newItem: ProductDb): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ProductDb, newItem: ProductDb): Boolean {
            return oldItem == newItem
        }
    }

    open class OnClickListener(val clickListener: (v: View, productDb: ProductDb) -> Unit) {
        fun onClick(v: View, productDb: ProductDb) = clickListener(v, productDb)
    }
}