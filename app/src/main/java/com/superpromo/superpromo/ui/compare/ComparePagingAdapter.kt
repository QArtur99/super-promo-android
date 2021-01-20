package com.superpromo.superpromo.ui.compare

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.superpromo.superpromo.GlideRequests
import com.superpromo.superpromo.R
import com.superpromo.superpromo.data.network.model.Product

class ComparePagingAdapter(
    private val glide: GlideRequests,
    private val clickListener: OnClickListener,
) : PagingDataAdapter<Product, RecyclerView.ViewHolder>(GridViewDiffCallback) {

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            R.layout.item_product ->
                (holder as MovieViewHolder).bind(clickListener, getItem(position))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            R.layout.item_product -> MovieViewHolder.create(parent, glide)
            else -> throw IllegalArgumentException("unknown view type $viewType")
        }
    }

    override fun getItemViewType(position: Int): Int {
        val item = getItem(position)!!
        return R.layout.item_product
    }

    class MovieViewHolder constructor(
        private val view: View,
        private val glide: GlideRequests
    ) : RecyclerView.ViewHolder(view) {

        companion object {
            fun create(parent: ViewGroup, glide: GlideRequests): MovieViewHolder {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_product, parent, false)
                return MovieViewHolder(view, glide)
            }
        }

        fun bind(clickListener: OnClickListener, item: Product?) {

        }
    }

    companion object GridViewDiffCallback : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }
    }

    open class OnClickListener(val clickListener: (v: View, product: Product) -> Unit) {
        fun onClick(v: View, product: Product) = clickListener(v, product)
    }

}