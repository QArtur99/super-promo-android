package com.superpromo.superpromo.repository

import androidx.paging.Pager
import androidx.paging.PagingData
import com.superpromo.superpromo.data.network.model.Product
import kotlinx.coroutines.flow.Flow

interface SuperPromoRepository {
    fun getProducts(shopId: Int, pageSize: Int) : Flow<PagingData<Product>>
}