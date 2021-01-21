package com.superpromo.superpromo.data.network

import com.superpromo.superpromo.data.network.model.ProductContainer
import com.superpromo.superpromo.data.network.model.Shop
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface SuperPromoApi {

    @GET("/shop/getShops")
    fun getShopListAsync(): Deferred<List<Shop>>

    @GET("/product/getProducts")
    fun getProductListAsync(
        @Query("shopId") shopId: Int? = null,
        @Query("page") page: Int? = null,
        @Query("limit") limit: Int? = null,
        @Query("product") product: String? = null,
    ): Deferred<ProductContainer>

}