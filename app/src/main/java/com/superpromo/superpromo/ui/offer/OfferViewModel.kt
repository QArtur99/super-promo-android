package com.superpromo.superpromo.ui.offer

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.superpromo.superpromo.data.network.model.Shop
import com.superpromo.superpromo.repository.SuperPromoRepository
import com.superpromo.superpromo.ui.data.SearchModel
import kotlinx.coroutines.launch

class OfferViewModel @ViewModelInject constructor(
    @Assisted private val savedStateHandle: SavedStateHandle,
    private val superPromoRepository: SuperPromoRepository
) : ViewModel() {
    companion object {
        const val KEY_SHOPS = "shopList"
        val DEFAULT_SHOP = SearchModel(7, "")
    }

    init {
        getShops()
    }

    fun getShops() {
        viewModelScope.launch {
            val shopList = superPromoRepository.getShops()
            savedStateHandle.set(KEY_SHOPS, shopList)
        }
    }

    val shops = savedStateHandle.getLiveData<List<Shop>>(KEY_SHOPS)


}