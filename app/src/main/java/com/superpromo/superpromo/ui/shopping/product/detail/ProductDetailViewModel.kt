package com.superpromo.superpromo.ui.shopping.product.detail

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.superpromo.superpromo.data.db.model.ProductDb
import com.superpromo.superpromo.repository.product.ProductRepository
import com.superpromo.superpromo.repository.shopping_list.ShoppingListRepository
import com.superpromo.superpromo.ui.util.Event
import kotlinx.coroutines.launch

class ProductDetailViewModel @ViewModelInject constructor(
    @Assisted private val savedStateHandle: SavedStateHandle,
    private val productRepository: ProductRepository,
    private val shoppingListRepository: ShoppingListRepository
) : ViewModel() {

    private var _productId: Long = 0

    private val _showAdd = MutableLiveData<Event<Boolean>>()
    val showAdd: LiveData<Event<Boolean>> = _showAdd

    private val _showDelete = MutableLiveData<Event<Boolean>>()
    val showDelete: LiveData<Event<Boolean>> = _showDelete

    private val _productIdNew = MutableLiveData<Long>()
    val productIdNew: LiveData<Long> = _productIdNew

    fun setProductId(productId: Long) {
        _productId = productId
    }

    fun insertProductDb(productDb: ProductDb) {
        viewModelScope.launch {
            _productIdNew.value = productRepository.insert(productDb)
            _showDelete.value = Event(true)
        }
    }

    fun deleteProductDb(productDb: ProductDb) {
        viewModelScope.launch {
            productRepository.delete(productDb.id)
            _showAdd.value = Event(true)
        }
    }
}
