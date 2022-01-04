package com.superpromo.superpromo.ui.shopping.product.detail_archive


import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.superpromo.superpromo.repository.product.ProductRepository
import com.superpromo.superpromo.repository.shopping_list.ShoppingListRepository
import dagger.assisted.Assisted
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProductArchiveDetailViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val productRepository: ProductRepository,
    private val shoppingListRepository: ShoppingListRepository
) : ViewModel() {

    private var _productId: Long = 0

    fun setProductId(productId: Long) {
        _productId = productId
    }
}
