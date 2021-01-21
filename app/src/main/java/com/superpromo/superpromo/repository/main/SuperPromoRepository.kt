package com.superpromo.superpromo.repository.main

import androidx.paging.PagingData
import com.superpromo.superpromo.data.network.model.Product
import com.superpromo.superpromo.data.network.model.Shop
import com.superpromo.superpromo.repository.state.ResultStatus
import kotlinx.coroutines.flow.Flow

interface SuperPromoRepository {
    suspend fun getShops(): ResultStatus<List<Shop>>
    fun getProducts(shopId: Int, pageSize: Int, product: String): Flow<PagingData<Product>>
}