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
        const val KEY_SHOPS = "KEY_SHOPS"
        const val KEY_SHOPS_AVAILABLE = "KEY_SHOPS_AVAILABLE"
        const val KEY_FILTER_SHOPS = "KEY_FILTER_SHOPS"
    }

    private val _shopErrorState = MutableLiveData<State>(State.Loading)
    private val shopErrorState: LiveData<State> = _shopErrorState
    private val _shopsFull = savedStateHandle.getLiveData<List<Shop>>(KEY_SHOPS)
    private val shopsFull = _shopsFull.map { it }
    private val _shopsAvailable = savedStateHandle.getLiveData<List<Shop>>(KEY_SHOPS_AVAILABLE)
    val shopsAvailable = _shopsAvailable.map { it }
    private val _filterByShopName = savedStateHandle.getLiveData<String>(KEY_FILTER_SHOPS)
    private val filterByShopName = _filterByShopName.map { it }
    val shopList = MediatorLiveData<ShopModel>().apply {
        value = ShopModel(emptyList(), "", State.Loading)
        addSourceInvoke(shopErrorState) { value?.state = it }
        addSourceInvoke(shopsFull) { value?.shopList = it }
    }
    val shops = MediatorLiveData<ShopModel>().apply {
        value = ShopModel(emptyList(), "", State.Loading)
        addSourceInvoke(shopErrorState) { value?.state = it }
        addSourceInvoke(shopsAvailable) { value?.shopList = it }
        addSourceInvoke(filterByShopName) { name ->
            if (name.isNullOrEmpty()) {
                value?.shopList = shopsAvailable.value ?: emptyList()
            } else {
                value?.shopList = shopsFull.value?.filter {
                    it.name.unaccent().contains(name.unaccent(), ignoreCase = true)
                } ?: emptyList()
            }
        }
    }

    init {
        savedStateHandle.set(KEY_SHOPS, emptyList<Shop>())
        savedStateHandle.set(KEY_SHOPS_AVAILABLE, emptyList<Shop>())
        savedStateHandle.set(KEY_FILTER_SHOPS, "")
        getShops()
    }

    fun getShops() {
        viewModelScope.launch {
            _shopErrorState.value = State.Loading
            when (val result = superPromoRepository.getShops()) {
                is ResultApi.Success -> {
                    getShopList(result.data)
                    _shopErrorState.value = State.Success
                }
                is ResultApi.Error -> {
                    Timber.e(result.message)
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
            shopsFull.value?.let { getShopList(it) }
        }
    }

    fun deleteShopAll() {
        viewModelScope.launch {
            superPromoRepository.deleteShopAll()
            shopsFull.value?.let { getShopList(it) }
        }
    }

    private fun getShopList(shopList: List<Shop>) {
        viewModelScope.launch {
            val list = superPromoRepository.getShopList()
            val map = list.map { it.id to it.isAvailableInDb }.toMap()
            val filter = shopList.filter { map[it.id] ?: false }
            setShopAvailableList(filter, shopList)
            setIsAvailableInDb(shopList, map)
        }
    }

    private fun setShopAvailableList(
        filter: List<Shop>,
        shopFull: List<Shop>
    ) {
        if (filter.isNullOrEmpty() && shopFull.isNotEmpty()) {
            savedStateHandle.set(KEY_SHOPS_AVAILABLE, shopFull)
        } else {
            savedStateHandle.set(KEY_SHOPS_AVAILABLE, filter)
        }
    }

    private fun setIsAvailableInDb(
        list: List<Shop>,
        map: Map<Int, Boolean?>
    ) {
        val newList = list.map { it.copy(isAvailableInDb = map[it.id] ?: false) }
        savedStateHandle.set(KEY_SHOPS, newList)
    }

}