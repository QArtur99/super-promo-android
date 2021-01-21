package com.superpromo.superpromo.data.network.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Error(
    val message: String?,
    val code: Int?,
)