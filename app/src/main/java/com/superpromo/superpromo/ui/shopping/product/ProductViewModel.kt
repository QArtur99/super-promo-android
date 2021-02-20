package com.superpromo.superpromo.ui.shopping.product

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.superpromo.superpromo.data.db.model.ShoppingListDb
import com.superpromo.superpromo.repository.product.ProductRepository
import com.superpromo.superpromo.repository.shopping_list.ShoppingListRepository
import kotlinx.coroutines.launch

class ProductViewModel @ViewModelInject constructor(
    @Assisted private val savedStateHandle: SavedStateHandle,
    private val productRepository: ProductRepository,
    private val shoppingListRepository: ShoppingListRepository
) : ViewModel() {

    private val _shoppingListId = MutableLiveData<Long>()
    val productList = _shoppingListId.switchMap {
        productRepository.getList(it).asLiveData()
    }

    fun setShoppingListId(shoppingListId: Long) {
        _shoppingListId.value = shoppingListId
    }

    fun editShoppingListDb(shoppingListDb: ShoppingListDb) {
        viewModelScope.launch {
            shoppingListRepository.insert(shoppingListDb)
        }
    }

    fun deleteShoppingListDb(shoppingListDb: ShoppingListDb) {
        viewModelScope.launch {
            shoppingListRepository.delete(shoppingListDb.id)
        }
    }


}