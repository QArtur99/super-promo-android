package com.superpromo.superpromo.ui.data

import android.os.Parcelable
import com.superpromo.superpromo.data.network.model.Shop
import com.superpromo.superpromo.repository.state.State
import kotlinx.parcelize.Parcelize

@Parcelize
class ShopModel(
    var shopList: List<Shop>,
    var filterByName: String,
    var state: State,
) : Parcelable