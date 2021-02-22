package com.superpromo.superpromo.ui.shopping.product.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.superpromo.superpromo.GlideRequests
import com.superpromo.superpromo.R
import com.superpromo.superpromo.data.db.model.ProductDb
import com.superpromo.superpromo.ui.util.TimeHelper
import java.util.*


class ProductTextViewHolder constructor(
    private val view: View,
    private val glide: GlideRequests,
    private val clickListener: ProductListAdapter.OnClickListener
) : RecyclerView.ViewHolder(view) {

    companion object {
        fun create(
            parent: ViewGroup,
            glide: GlideRequests,
            clickListener: ProductListAdapter.OnClickListener
        ): ProductTextViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_shopping_list, parent, false)
            return ProductTextViewHolder(view, glide, clickListener)
        }
    }

    private val name: TextView = view.findViewById(R.id.name)
    private val info: TextView = view.findViewById(R.id.info)
    private val date: TextView = view.findViewById(R.id.date)
    private var productDb: ProductDb? = null

    init {
        view.setOnClickListener {
            productDb?.let { clickListener.onClick(view, it) }
        }
    }

    fun bind(item: ProductDb) {
        productDb = item
//        name.text = item.name
//        info.text = "4 products"
//        date.text = TimeHelper.getDateFormat().format(Date(item.created))
    }
}