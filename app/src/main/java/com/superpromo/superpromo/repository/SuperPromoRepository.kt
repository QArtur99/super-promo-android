package com.superpromo.superpromo.repository

import androidx.paging.PagingData
import com.superpromo.superpromo.data.network.model.Product
import com.superpromo.superpromo.data.network.model.Shop
import kotlinx.coroutines.flow.Flow

interface SuperPromoRepository {
    suspend fun getShops(): List<Shop>
    fun getProducts(shopId: Int, pageSize: Int, product: String): Flow<PagingData<Product>>
}