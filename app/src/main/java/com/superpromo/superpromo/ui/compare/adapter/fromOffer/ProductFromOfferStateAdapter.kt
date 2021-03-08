package com.superpromo.superpromo.ui.compare.adapter.fromOffer

import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import com.superpromo.superpromo.ui.compare.adapter.load.NetworkStateItemViewHolder

class ProductFromOfferStateAdapter(
    private val adapter: ProductFromOfferPagingAdapter
) : LoadStateAdapter<NetworkStateItemViewHolder>() {
    override fun onBindViewHolder(holder: NetworkStateItemViewHolder, loadState: LoadState) {
        holder.bindTo(loadState)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): NetworkStateItemViewHolder {
        return NetworkStateItemViewHolder(parent) { adapter.retry() }
    }
}
