package com.superpromo.superpromo.ui.shopping.product.detail_archive

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.superpromo.superpromo.repository.product.ProductRepository
import com.superpromo.superpromo.repository.shopping_list.ShoppingListRepository

class ProductArchiveDetailViewModel @ViewModelInject constructor(
    @Assisted private val savedStateHandle: SavedStateHandle,
    private val productRepository: ProductRepository,
    private val shoppingListRepository: ShoppingListRepository
) : ViewModel() {

    private var _productId: Long = 0

    fun setProductId(productId: Long) {
        _productId = productId
    }
}
