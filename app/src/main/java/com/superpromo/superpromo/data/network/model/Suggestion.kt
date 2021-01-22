package com.superpromo.superpromo.data.network.model

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class Suggestion(
    val suggestion: String,
) : Parcelable