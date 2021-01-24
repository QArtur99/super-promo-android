package com.superpromo.superpromo.ui.main

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.superpromo.superpromo.data.network.model.Shop
import com.superpromo.superpromo.repository.main.SuperPromoRepository
import com.superpromo.superpromo.repository.state.ResultStatus
import kotlinx.coroutines.launch
import timber.log.Timber

class SharedProductVm @ViewModelInject constructor(
    @Assisted private val savedStateHandle: SavedStateHandle,
    private val superPromoRepository: SuperPromoRepository
) : ViewModel() {
    companion object {
        const val KEY_SHOPS = "shopList"
        const val KEY_FILTER_SHOPS = "shopFilter"
    }

    private val _shopsFull = savedStateHandle.getLiveData<List<Shop>>(KEY_SHOPS)
    private val shopsFull = _shopsFull.map { it }
    private val _filterByShopName = savedStateHandle.getLiveData<String>(KEY_FILTER_SHOPS)
    private val filterByShopName = _filterByShopName.map { it }
    val shops = MediatorLiveData<List<Shop>>().apply {
        addSource(shopsFull) { value = it }
        addSource(filterByShopName) { name ->
            value = shopsFull.value?.filter {
                it.name.contains(name, ignoreCase = true)
            } ?: return@addSource
        }
    }

    init {
        getShops()
        savedStateHandle.set(KEY_SHOPS, emptyList<Shop>())
        savedStateHandle.set(KEY_FILTER_SHOPS, "")
    }

    fun getShops() {
        viewModelScope.launch {
            when (val shopList = superPromoRepository.getShops()) {
                is ResultStatus.Success -> {
                    savedStateHandle.set(KEY_SHOPS, shopList.data)
                }
                is ResultStatus.Error -> {
                    Timber.e(shopList.message)
                }
            }
        }
    }

    fun showShops(query: String) {
        savedStateHandle.set(KEY_FILTER_SHOPS, query)
    }

}