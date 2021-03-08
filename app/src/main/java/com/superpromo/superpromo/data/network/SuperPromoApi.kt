package com.superpromo.superpromo.data.network

import com.superpromo.superpromo.data.network.model.ProductContainer
import com.superpromo.superpromo.data.network.model.Shop
import com.superpromo.superpromo.data.network.model.Suggestion
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

interface SuperPromoApi {

    @GET("/shop/getShops")
    fun getShopListAsync(): Deferred<List<Shop>>

    @GET("/product/getSuggestions")
    fun getProductSuggestionListAsync(
        @Query("categoryId") categoryId: Int? = null
    ): Deferred<List<Suggestion>>

    @GET("/product/getProducts")
    fun getProductListAsync(
        @Query("shopIds") shopIds: String? = null,
        @Query("page") page: Int? = null,
        @Query("limit") limit: Int? = null,
        @Query("product") product: String? = null,
    ): Deferred<ProductContainer>
}
