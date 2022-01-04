package com.superpromo.superpromo.ui.shopping.list


import androidx.lifecycle.*
import com.superpromo.superpromo.data.db.model.ShoppingListDb
import com.superpromo.superpromo.repository.shopping_list.ShoppingListRepository
import dagger.assisted.Assisted
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShoppingListViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val shoppingListRepository: ShoppingListRepository
) : ViewModel() {

    private val _shoppingLists = shoppingListRepository.getAll().asLiveData()
    val shoppingLists: LiveData<List<ShoppingListDb>> = _shoppingLists.map {
        mutableListOf<ShoppingListDb>().apply {
            add(ShoppingListDb())
            addAll(it)
        }
    }

    fun addShoppingListDb(shoppingListDb: ShoppingListDb) {
        viewModelScope.launch {
            shoppingListRepository.insert(shoppingListDb)
        }
    }
}
