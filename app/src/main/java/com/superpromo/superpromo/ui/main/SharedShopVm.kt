package com.superpromo.superpromo.ui.main

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.superpromo.superpromo.data.network.model.Shop
import com.superpromo.superpromo.repository.main.SuperPromoRepository
import com.superpromo.superpromo.repository.state.ResultApi
import com.superpromo.superpromo.repository.state.State
import com.superpromo.superpromo.ui.data.ShopModel
import com.superpromo.superpromo.ui.util.ext.addSourceInvoke
import com.superpromo.superpromo.ui.util.unaccent
import kotlinx.coroutines.launch
import timber.log.Timber

class SharedShopVm @ViewModelInject constructor(
    @Assisted private val savedStateHandle: SavedStateHandle,
    private val superPromoRepository: SuperPromoRepository
) : ViewModel() {
    companion object {
        const val KEY_SHOPS = "shopList"
        const val KEY_FILTER_SHOPS = "shopFilter"
    }

    private val _shopErrorState = MutableLiveData<State>(State.Loading)
    private val shopErrorState: LiveData<State> = _shopErrorState
    private val _shopsFull = savedStateHandle.getLiveData<List<Shop>>(KEY_SHOPS)
    private val shopsFull = _shopsFull.map { it }
    private val _filterByShopName = savedStateHandle.getLiveData<String>(KEY_FILTER_SHOPS)
    private val filterByShopName = _filterByShopName.map { it }
    val shops = MediatorLiveData<ShopModel>().apply {
        value = ShopModel(emptyList(), "", State.Loading)
        addSourceInvoke(shopErrorState) { value?.state = it }
        addSourceInvoke(shopsFull) { value?.shopList = it }
        addSourceInvoke(filterByShopName) { name ->
            value?.shopList = shopsFull.value?.filter {
                it.name.unaccent().contains(name.unaccent(), ignoreCase = true)
            } ?: emptyList()
        }
    }

    init {
        getShops()
        savedStateHandle.set(KEY_SHOPS, emptyList<Shop>())
        savedStateHandle.set(KEY_FILTER_SHOPS, "")
    }

    fun getShops() {
        viewModelScope.launch {
            _shopErrorState.value = State.Loading
            when (val shopList = superPromoRepository.getShops()) {
                is ResultApi.Success -> {
                    savedStateHandle.set(KEY_SHOPS, shopList.data)
                    _shopErrorState.value = State.Success
                }
                is ResultApi.Error -> {
                    Timber.e(shopList.message)
                    _shopErrorState.value = State.Error
                }
            }
        }
    }

    fun showShops(query: String) {
        savedStateHandle.set(KEY_FILTER_SHOPS, query)
    }

    fun insertShop(shop: Shop) {
        viewModelScope.launch {
            superPromoRepository.insertShop(shop)
        }
    }

}