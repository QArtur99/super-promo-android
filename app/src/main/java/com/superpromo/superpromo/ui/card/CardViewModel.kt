package com.superpromo.superpromo.ui.card

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.superpromo.superpromo.R
import com.superpromo.superpromo.repository.main.SuperPromoRepository
import com.superpromo.superpromo.ui.data.CardColorModel
import com.superpromo.superpromo.ui.data.CardModel

class CardViewModel @ViewModelInject constructor(
    @Assisted private val savedStateHandle: SavedStateHandle,
    private val superPromoRepository: SuperPromoRepository
) : ViewModel() {

    private val _cardList = MutableLiveData<List<CardModel>>()
    val cardList: LiveData<List<CardModel>> = _cardList

    private val _cardColorList = MutableLiveData<List<CardColorModel>>()
    val cardColorList: LiveData<List<CardColorModel>> = _cardColorList

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
        _cardColorList.value = listOf(
            CardColorModel(R.color.black_700),
            CardColorModel(R.color.blue_grey_700),
            CardColorModel(R.color.deep_orange_700),
            CardColorModel(R.color.orange_700),
            CardColorModel(R.color.amber_700),
            CardColorModel(R.color.yellow_700),
            CardColorModel(R.color.lime_700),
            CardColorModel(R.color.light_green_700),
            CardColorModel(R.color.green_700),
            CardColorModel(R.color.teal_700),
            CardColorModel(R.color.cyan_700),
            CardColorModel(R.color.light_blue_700),
            CardColorModel(R.color.blue_700),
            CardColorModel(R.color.indigo_700),
            CardColorModel(R.color.deep_purple_700),
            CardColorModel(R.color.purple_700),
            CardColorModel(R.color.pink_700),
            CardColorModel(R.color.red_700),
        ).reversed()
    }


}