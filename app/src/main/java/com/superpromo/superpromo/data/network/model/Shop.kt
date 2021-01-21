package com.superpromo.superpromo.data.network.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Shop (
    val id: Int,
    val name: String,
    val imgUrl: String?,
    val url: String?,
    val productCount: Int?,
    val isAvailable: Boolean?,
)