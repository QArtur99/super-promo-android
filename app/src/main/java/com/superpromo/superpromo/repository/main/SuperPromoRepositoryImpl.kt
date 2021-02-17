package com.superpromo.superpromo.repository.main

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.superpromo.superpromo.data.db.SuperPromoDb
import com.superpromo.superpromo.data.db.model.ShopDb
import com.superpromo.superpromo.data.network.SuperPromoApi
import com.superpromo.superpromo.data.network.model.Shop
import com.superpromo.superpromo.data.network.model.Suggestion
import com.superpromo.superpromo.di.DefaultDispatcher
import com.superpromo.superpromo.di.IoDispatcher
import com.superpromo.superpromo.repository.BaseRepository
import com.superpromo.superpromo.repository.main.mapper.asDbModel
import com.superpromo.superpromo.repository.state.ResultApi
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SuperPromoRepositoryImpl @Inject constructor(
    private val superPromoDb: SuperPromoDb,
    private val superPromoApi: SuperPromoApi,
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : BaseRepository(ioDispatcher), SuperPromoRepository {

    private val shopMap = HashMap<Int, Shop>()

    override suspend fun getShops(): ResultApi<List<Shop>> = safeApiCall(
        call = {
            val result = superPromoApi.getShopListAsync().await()
            shopsToMap(result)
            result
        },
        errorMessage = "getShops error",
    )

    private suspend fun shopsToMap(result: List<Shop>) {
        withContext(defaultDispatcher) {
            result.forEach { shopMap[it.id] = it }
        }
    }

    override suspend fun getProductSuggestions(): ResultApi<List<Suggestion>> = safeApiCall(
        call = {
            superPromoApi.getProductSuggestionListAsync(1).await()
        },
        errorMessage = "getProductSuggestions error",
    )

    override fun getProducts(
        shopIds: String,
        pageSize: Int,
        product: String
    ) = Pager(PagingConfig(pageSize)) {
        ProductPagingSource(
            superPromoApi = superPromoApi,
            shopMap = shopMap,
            shopIds = shopIds,
            limit = pageSize,
            product = product,
        )
    }.flow

    override suspend fun insertShop(shop: Shop) {
        withContext(ioDispatcher) {
            superPromoDb.shopDao().insert(shop.asDbModel())
        }
    }

    override suspend fun deleteShopAll() {
        withContext(ioDispatcher) {
            superPromoDb.shopDao().deleteAll()
        }
    }

    override suspend fun getShopList(): List<ShopDb> {
        return withContext(ioDispatcher) {
            superPromoDb.shopDao().getItemAll()
        }
    }

}