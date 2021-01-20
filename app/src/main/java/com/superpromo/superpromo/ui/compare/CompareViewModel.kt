package com.superpromo.superpromo.ui.compare

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.superpromo.superpromo.data.network.model.Product
import com.superpromo.superpromo.repository.SuperPromoRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*

class CompareViewModel @ViewModelInject constructor(
    @Assisted private val savedStateHandle: SavedStateHandle,
    private val superPromoRepository: SuperPromoRepository
) : ViewModel() {
    companion object {
        const val KEY_SHOP = "shopId"
        const val DEFAULT_SHOP = 7
    }

    private val clearListCh = Channel<Unit>(Channel.CONFLATED)

    init {
        if (!savedStateHandle.contains(KEY_SHOP)) {
            savedStateHandle.set(KEY_SHOP, DEFAULT_SHOP)
        }
    }

    val posts = flowOf(
        clearListCh.receiveAsFlow().map { PagingData.empty() },
        savedStateHandle.getLiveData<Int>(KEY_SHOP)
            .asFlow()
            .flatMapLatest { superPromoRepository.getProducts(7, 20) }
            .cachedIn(viewModelScope)
    ).flattenMerge()

    private fun shouldShowShop(
        shopId: String
    ) = savedStateHandle.get<String>(KEY_SHOP) != shopId

    fun showShop(shopId: String) {
        if (!shouldShowShop(shopId)) return
        clearListCh.offer(Unit)
        savedStateHandle.set(KEY_SHOP, shopId)
    }
}