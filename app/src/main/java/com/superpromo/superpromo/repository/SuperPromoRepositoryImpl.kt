package com.superpromo.superpromo.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.superpromo.superpromo.data.network.SuperPromoApi
import com.superpromo.superpromo.di.DefaultDispatcher
import com.superpromo.superpromo.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class SuperPromoRepositoryImpl @Inject constructor(
    private val superPromoApi: SuperPromoApi,
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : SuperPromoRepository {

//    override suspend fun getShops(sortBy: String, pageNo: String): MovieContainer {
//        return superPromoApi.getMoviesAsync(sortBy, args).await()
//    }

    override fun getProducts(shopId: Int, pageSize: Int) = Pager(
        PagingConfig(pageSize)
    ) {
        ProductPagingSource(
            superPromoApi = superPromoApi,
            shopId = shopId,
            limit = 20,
        )
    }.flow

}