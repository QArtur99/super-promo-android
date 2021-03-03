package com.superpromo.superpromo.ui.shopping.product.detail_archive

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.superpromo.superpromo.data.db.model.ProductDb
import com.superpromo.superpromo.data.db.model.ShoppingListDb
import com.superpromo.superpromo.repository.product.ProductRepository
import com.superpromo.superpromo.repository.shopping_list.ShoppingListRepository
import com.superpromo.superpromo.ui.util.Event
import com.superpromo.superpromo.ui.util.ext.toInt
import kotlinx.coroutines.launch

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