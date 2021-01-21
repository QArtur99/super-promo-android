package com.superpromo.superpromo.repository.main

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.superpromo.superpromo.data.network.SuperPromoApi
import com.superpromo.superpromo.data.network.model.Shop
import com.superpromo.superpromo.di.DefaultDispatcher
import com.superpromo.superpromo.di.IoDispatcher
import com.superpromo.superpromo.repository.BaseRepository
import com.superpromo.superpromo.repository.state.ResultStatus
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class SuperPromoRepositoryImpl @Inject constructor(
    private val superPromoApi: SuperPromoApi,
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : BaseRepository(ioDispatcher), SuperPromoRepository {

    override suspend fun getShops(): ResultStatus<List<Shop>> = safeApiCall(
        call = { superPromoApi.getShopListAsync().await() },
        errorMessage = "getShops error",
    )

    override fun getProducts(
        shopId: Int?,
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