package com.superpromo.superpromo.ui.card_detail

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.superpromo.superpromo.R
import com.superpromo.superpromo.data.db.model.CardDb
import com.superpromo.superpromo.repository.card.CardRepository
import com.superpromo.superpromo.ui.data.CardColorModel
import kotlinx.coroutines.launch

class CardDetailViewModel @ViewModelInject constructor(
    @Assisted private val savedStateHandle: SavedStateHandle,
    private val cardRepository: CardRepository,
) : ViewModel() {

    private val _cardList = MutableLiveData<List<CardDb>>()
    val cardList: LiveData<List<CardDb>> = _cardList

    private val _cardColorList = MutableLiveData<List<CardColorModel>>()
    val cardColorList: LiveData<List<CardColorModel>> = _cardColorList

    init {
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
        getCardList()
    }

    fun getCardList() {

    }

    fun addCard(name: String, color: String, number: String, formatName: String) {
        val card = CardDb(0, name, color, number, formatName)
        viewModelScope.launch {
            cardRepository.insertCard(card)
        }
    }


}