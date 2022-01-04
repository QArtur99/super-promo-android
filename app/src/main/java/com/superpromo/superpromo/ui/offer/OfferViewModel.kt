package com.superpromo.superpromo.ui.offer

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.superpromo.superpromo.repository.main.SuperPromoRepository
import dagger.assisted.Assisted
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OfferViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val superPromoRepository: SuperPromoRepository
) : ViewModel()
