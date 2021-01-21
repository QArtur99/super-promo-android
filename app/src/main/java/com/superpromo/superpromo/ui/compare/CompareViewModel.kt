package com.superpromo.superpromo.ui.compare

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.superpromo.superpromo.repository.SuperPromoRepository
import com.superpromo.superpromo.ui.data.SearchModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*

class CompareViewModel @ViewModelInject constructor(
    @Assisted private val savedStateHandle: SavedStateHandle,
    private val superPromoRepository: SuperPromoRepository
) : ViewModel() {
    companion object {
        const val KEY_SHOP = "shopId"
        val DEFAULT_SHOP = SearchModel(7, "")
    }

    private val clearListCh = Channel<Unit>(Channel.CONFLATED)

    init {
        if (!savedStateHandle.contains(KEY_SHOP)) {
            savedStateHandle.set(KEY_SHOP, DEFAULT_SHOP)
        }
    }

    val posts = flowOf(
        clearListCh.receiveAsFlow().map { PagingData.empty() },
        savedStateHandle.getLiveData<SearchModel>(KEY_SHOP)
            .asFlow()
            .flatMapLatest { superPromoRepository.getProducts(it.shopId, 20, it.product) }
            .cachedIn(viewModelScope)
    ).flattenMerge()

    fun showShop(shopId: Int) {
        if (!shouldShowShop(shopId)) return
        clearListCh.offer(Unit)
        savedStateHandle.get<SearchModel>(KEY_SHOP)?.apply {
            val model = SearchModel(shopId, this.product)
            savedStateHandle.set(KEY_SHOP, model)
        }
    }

    fun showProducts(product: String) {
        if (!shouldShowProduct(product)) return
        clearListCh.offer(Unit)
        savedStateHandle.get<SearchModel>(KEY_SHOP)?.apply {
            val model = SearchModel(this.shopId, product)
            savedStateHandle.set(KEY_SHOP, model)
        }
    }

    private fun shouldShowShop(
        shopId: Int
    ) = savedStateHandle.get<SearchModel>(KEY_SHOP)?.shopId != shopId

    private fun shouldShowProduct(
        product: String
    ) = savedStateHandle.get<SearchModel>(KEY_SHOP)?.product != product
}