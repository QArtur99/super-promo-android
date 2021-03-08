package com.superpromo.superpromo.ui.menu_filter

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.superpromo.superpromo.repository.main.SuperPromoRepository

class FilterOfferViewModel @ViewModelInject constructor(
    @Assisted private val savedStateHandle: SavedStateHandle,
    private val superPromoRepository: SuperPromoRepository
) : ViewModel()
