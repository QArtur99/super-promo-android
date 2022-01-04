package com.superpromo.superpromo.repository.main

import androidx.paging.PagingSource
import androidx.paging.PagingSource.LoadResult.Page
import androidx.paging.PagingState
import com.superpromo.superpromo.data.network.SuperPromoApi
import com.superpromo.superpromo.data.network.model.Product
import com.superpromo.superpromo.data.network.model.Shop
import retrofit2.HttpException
import java.io.IOException

class ProductPagingSource(
    private val superPromoApi: SuperPromoApi,
    private val shopMap: HashMap<Int, Shop>,
    private val shopIds: String,
    private val limit: Int,
    private val product: String,
) : PagingSource<String, Product>() {
    override suspend fun load(params: LoadParams<String>): LoadResult<String, Product> {
        return try {
            val data = superPromoApi.getProductListAsync(
                shopIds = shopIds,
                page = if (params.key.isNullOrEmpty()) 0 else params.key?.toInt(),
                limit = limit,
                product = product,
            ).await()

            Page(
                data = data.productList.map {
                    it.apply {
                        shopName = shopMap[it.shopId.toInt()]?.name
                    }
                },
                prevKey = data.prevKey,
                nextKey = data.nextKey
            )
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<String, Product>): String {
        return ""
    }
}
