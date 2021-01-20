package com.superpromo.superpromo.data.network.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ProductContainer(
    @Json(name = "productList") val productList: List<Product>,
    val prevKey: String?,
    val nextKey: String?,
    val total_results: Int?,
    val total_pages: Int?
)

@JsonClass(generateAdapter = true)
data class Product(
    val id: Int,
    val shopId: Int,
    val name: String?,
    val subtitle: String?,
    val price: Double?,
    val amount: String?,
    val details: String?,
    val promoInfo: String?,
    val promo: String?,
    val imgUrl: String?,
    val url: String?,
    val isOnlyImg: Boolean?,
)