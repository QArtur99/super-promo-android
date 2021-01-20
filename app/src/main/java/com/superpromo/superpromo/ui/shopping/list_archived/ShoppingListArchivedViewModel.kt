package com.superpromo.superpromo.ui.shopping.list_archived

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.superpromo.superpromo.repository.SuperPromoRepository

class ShoppingListArchivedViewModel @ViewModelInject constructor(
    @Assisted private val savedStateHandle: SavedStateHandle,
    private val superPromoRepository: SuperPromoRepository
) : ViewModel() {


}