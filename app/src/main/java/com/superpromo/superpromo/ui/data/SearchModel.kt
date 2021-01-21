package com.superpromo.superpromo.ui.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SearchModel(val shopId: Int, val product: String) : Parcelable