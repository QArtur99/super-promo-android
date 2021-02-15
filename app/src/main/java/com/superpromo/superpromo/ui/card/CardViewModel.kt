package com.superpromo.superpromo.ui.card

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.superpromo.superpromo.R
import com.superpromo.superpromo.repository.main.SuperPromoRepository
import com.superpromo.superpromo.ui.data.CardModel

class CardViewModel @ViewModelInject constructor(
    @Assisted private val savedStateHandle: SavedStateHandle,
    private val superPromoRepository: SuperPromoRepository
) : ViewModel() {

    private val _cardList = MutableLiveData<List<CardModel>>()
    val cardList: LiveData<List<CardModel>> = _cardList

    init {
        _cardList.value = listOf(
            CardModel(R.drawable.ic_baseline_add_circle_24, ""),
            CardModel(R.drawable.ic_baseline_shopping_cart_24, "Biedronka"),
            CardModel(R.drawable.ic_baseline_credit_card_24, "Biedronka"),
            CardModel(R.drawable.ic_baseline_favorite_24, "Biedronka"),
            CardModel(R.drawable.ic_baseline_share_24, "Biedronka"),
            CardModel(R.drawable.ic_baseline_email_24, "Biedronka"),
            CardModel(R.drawable.ic_baseline_settings_24, "Biedronka"),
            CardModel(R.drawable.ic_baseline_info_24, "Biedronka")
        )
    }


}