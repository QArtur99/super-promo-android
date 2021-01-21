package com.superpromo.superpromo.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.superpromo.superpromo.data.network.SuperPromoApi
import com.superpromo.superpromo.data.network.model.Shop
import com.superpromo.superpromo.di.DefaultDispatcher
import com.superpromo.superpromo.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SuperPromoRepositoryImpl @Inject constructor(
    private val superPromoApi: SuperPromoApi,
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : SuperPromoRepository {

    override suspend fun getShops(): List<Shop> {
        return withContext(ioDispatcher) {
            superPromoApi.getShopListAsync().await()
        }
    }

    override fun getProducts(
        shopId: Int,
        pageSize: Int,
        product: String
    ) = Pager(PagingConfig(pageSize)) {
        ProductPagingSource(
            superPromoApi = superPromoApi,
            shopId = shopId,
            limit = 20,
            product = product,
        )
    }.flow

}