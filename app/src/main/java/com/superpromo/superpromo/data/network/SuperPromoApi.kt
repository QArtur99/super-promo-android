package com.superpromo.superpromo.data.network

import com.superpromo.superpromo.data.network.model.ProductContainer
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface SuperPromoApi {

    @GET("/movie/{sortBy}")
    fun getShopListAsync(
        @Path(value = "sortBy", encoded = true) sortBy: String,
        @QueryMap args: Map<String, String>
    ): Deferred<ProductContainer>

    @GET("/product/getProducts")
    fun getProductListAsync(
        @Query("shopId") shopId: Int? = null,
        @Query("page") page: Int? = null,
        @Query("limit") limit: Int? = null,
    ): Deferred<ProductContainer>


}