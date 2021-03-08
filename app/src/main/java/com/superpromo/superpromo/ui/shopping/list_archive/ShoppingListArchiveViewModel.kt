package com.superpromo.superpromo.ui.shopping.list_archive

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.superpromo.superpromo.data.db.model.ShoppingListDb
import com.superpromo.superpromo.repository.shopping_list.ShoppingListRepository
import kotlinx.coroutines.launch

class ShoppingListArchiveViewModel @ViewModelInject constructor(
    @Assisted private val savedStateHandle: SavedStateHandle,
    private val shoppingListRepository: ShoppingListRepository
) : ViewModel() {

    private val _shoppingLists = shoppingListRepository.getAllArchived().asLiveData()
    val shoppingLists: LiveData<List<ShoppingListDb>> = _shoppingLists.map {
        mutableListOf<ShoppingListDb>().apply {
            addAll(it)
        }
    }

    fun addShoppingListDb(shoppingListDb: ShoppingListDb) {
        viewModelScope.launch {
            shoppingListRepository.insert(shoppingListDb)
        }
    }
}
