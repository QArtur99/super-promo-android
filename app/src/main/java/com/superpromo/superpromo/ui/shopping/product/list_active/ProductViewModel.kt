package com.superpromo.superpromo.ui.shopping.product.list_active


import androidx.lifecycle.*
import com.superpromo.superpromo.data.db.model.ProductDb
import com.superpromo.superpromo.data.db.model.ShoppingListDb
import com.superpromo.superpromo.repository.product.ProductRepository
import com.superpromo.superpromo.repository.shopping_list.ShoppingListRepository
import dagger.assisted.Assisted
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val productRepository: ProductRepository,
    private val shoppingListRepository: ShoppingListRepository
) : ViewModel() {

    private val _shoppingListId = MutableLiveData<Long>()
    val productList = _shoppingListId.switchMap {
        productRepository.getList(it).asLiveData()
    }

    fun updateProductDb(productDb: ProductDb) {
        viewModelScope.launch {
            productRepository.insert(productDb)
        }
    }

    fun setShoppingListId(shoppingListId: Long) {
        _shoppingListId.value = shoppingListId
    }

    fun updateShoppingListDb(shoppingListDb: ShoppingListDb) {
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
