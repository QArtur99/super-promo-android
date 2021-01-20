package com.superpromo.superpromo.repository

import androidx.paging.PagingSource
import androidx.paging.PagingSource.LoadResult.Page
import com.superpromo.superpromo.data.network.SuperPromoApi
import com.superpromo.superpromo.data.network.model.Product
import retrofit2.HttpException
import java.io.IOException

class ProductPagingSource(
    private val superPromoApi: SuperPromoApi,
    private val shopId: Int,
    private val limit: Int,
) : PagingSource<String, Product>() {
    override suspend fun load(params: LoadParams<String>): LoadResult<String, Product> {
        return try {
            val data = superPromoApi.getProductListAsync(
                shopId = shopId,
                page = params.key?.toInt(),
                limit = limit
            ).await()

            Page(
                data = data.productList,
                prevKey = data.prevKey,
                nextKey = data.nextKey
            )
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }
    }
}
