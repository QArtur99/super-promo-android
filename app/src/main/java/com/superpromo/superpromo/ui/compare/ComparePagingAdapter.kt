package com.superpromo.superpromo.ui.compare

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.superpromo.superpromo.GlideRequests
import com.superpromo.superpromo.R
import com.superpromo.superpromo.data.network.model.Product
import com.superpromo.superpromo.ui.util.FormatPrice

class ComparePagingAdapter(
    private val glide: GlideRequests,
    private val clickListener: OnClickListener,
) : PagingDataAdapter<Product, RecyclerView.ViewHolder>(GridViewDiffCallback) {

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            R.layout.item_product ->
                (holder as ProductViewHolder).bind(clickListener, getItem(position))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            R.layout.item_product -> ProductViewHolder.create(parent, glide)
            else -> throw IllegalArgumentException("unknown view type $viewType")
        }
    }

    override fun getItemViewType(position: Int): Int {
        val item = getItem(position)!!
        return R.layout.item_product
    }

    class ProductViewHolder constructor(
        private val view: View,
        private val glide: GlideRequests
    ) : RecyclerView.ViewHolder(view) {

        companion object {
            fun create(parent: ViewGroup, glide: GlideRequests): ProductViewHolder {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_product, parent, false)
                return ProductViewHolder(view, glide)
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
                product?.let { url ->
//                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
//                    view.context.startActivity(intent)
                }
            }
        }

        fun bind(clickListener: OnClickListener, item: Product?) {
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

            }
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