package com.superpromo.superpromo.ui.compare

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.asLiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.superpromo.superpromo.data.db.model.ShoppingListDb
import com.superpromo.superpromo.data.network.model.Product
import com.superpromo.superpromo.data.network.model.Shop
import com.superpromo.superpromo.repository.main.SuperPromoRepository
import com.superpromo.superpromo.repository.mapper.asDbModel
import com.superpromo.superpromo.repository.product.ProductRepository
import com.superpromo.superpromo.repository.shopping_list.ShoppingListRepository
import com.superpromo.superpromo.ui.data.SearchModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flattenMerge
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class CompareViewModel @ViewModelInject constructor(
    @Assisted private val savedStateHandle: SavedStateHandle,
    private val superPromoRepository: SuperPromoRepository,
    private val shoppingListRepository: ShoppingListRepository,
    private val productRepository: ProductRepository,
) : ViewModel() {
    companion object {
        const val KEY_SHOP = "shopId&CompareViewModel"
        val DEFAULT_SHOP = SearchModel("", "")
    }

    private val clearListCh = Channel<Unit>(Channel.CONFLATED)

    private val _shoppingLists = shoppingListRepository.getAll().asLiveData()
    val shoppingLists: LiveData<List<ShoppingListDb>> = _shoppingLists.map {
        mutableListOf<ShoppingListDb>().apply {
            addAll(it)
        }
    }

    init {
        if (!savedStateHandle.contains(KEY_SHOP)) {
            savedStateHandle.set(KEY_SHOP, DEFAULT_SHOP)
        }
    }

    val posts = flowOf(
        clearListCh.receiveAsFlow().map { PagingData.empty() },
        savedStateHandle.getLiveData<SearchModel>(KEY_SHOP)
            .asFlow()
            .flatMapLatest {
                if (it.shopId.isNotEmpty()) {
                    superPromoRepository.getProducts(it.shopId, 20, it.product)
                } else {
                    emptyFlow()
                }
            }
            .cachedIn(viewModelScope)
    ).flattenMerge()

    fun showShop(shopId: Int) {
        val shopIds = arrayOf(shopId).joinToString("-")
        if (!shouldShowShop(shopIds)) return
        clearListCh.offer(Unit)
        savedStateHandle.get<SearchModel>(KEY_SHOP)?.apply {
            val model = SearchModel(shopIds, this.product)
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

    fun showProducts(shopList: List<Shop>, product: String) {
        val shopIds = shopList.map { it.id }.joinToString("-")
        if (shouldShowProduct(product) || shouldShowShop(shopIds)) {
            clearListCh.offer(Unit)
            savedStateHandle.get<SearchModel>(KEY_SHOP)?.apply {
                val model = SearchModel(shopIds, product)
                savedStateHandle.set(KEY_SHOP, model)
            }
        }
    }

    private fun shouldShowShop(
        shopId: String
    ) = savedStateHandle.get<SearchModel>(KEY_SHOP)?.shopId != shopId

    private fun shouldShowProduct(
        product: String
    ) = savedStateHandle.get<SearchModel>(KEY_SHOP)?.product != product

    fun updateProductDb(product: Product, shoppingListId: Long) {
        val productDb = product.asDbModel().run {
            copy(shoppingListId = shoppingListId)
        }
        viewModelScope.launch {
            productRepository.insert(productDb)
        }
    }

    fun updateShoppingListDb(shoppingListDb: ShoppingListDb) {
        viewModelScope.launch {
            shoppingListRepository.insert(shoppingListDb)
        }
    }
}
