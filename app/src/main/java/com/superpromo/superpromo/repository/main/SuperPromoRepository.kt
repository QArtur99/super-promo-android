package com.superpromo.superpromo.repository.main

import androidx.paging.PagingData
import com.superpromo.superpromo.data.db.model.ShopDb
import com.superpromo.superpromo.data.network.model.Product
import com.superpromo.superpromo.data.network.model.Shop
import com.superpromo.superpromo.data.network.model.Suggestion
import com.superpromo.superpromo.repository.state.ResultApi
import kotlinx.coroutines.flow.Flow

interface SuperPromoRepository {
    suspend fun getShops(): ResultApi<List<Shop>>
    suspend fun getProductSuggestions(): ResultApi<List<Suggestion>>
    fun getProducts(shopIds: String, pageSize: Int, product: String): Flow<PagingData<Product>>

    suspend fun insertShop(shop: Shop)
    suspend fun deleteShopAll()
    suspend fun getShopList(): List<ShopDb>
}
