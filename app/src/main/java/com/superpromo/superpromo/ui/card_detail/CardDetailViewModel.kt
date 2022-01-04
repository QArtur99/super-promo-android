package com.superpromo.superpromo.ui.card_detail

import androidx.lifecycle.*
import com.superpromo.superpromo.data.db.model.CardDb
import com.superpromo.superpromo.repository.card.CardRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CardDetailViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val cardRepository: CardRepository,
) : ViewModel() {

    private val _cardList = MutableLiveData<List<CardDb>>()
    val cardList: LiveData<List<CardDb>> = _cardList

    init {
    }

    fun deleteCard(id: Long) {
        viewModelScope.launch {
            cardRepository.delete(id)
        }
    }

    fun addCard(name: String, color: String, number: String, formatName: String) {
        val card = CardDb(0, name, color, number, formatName)
        viewModelScope.launch {
            cardRepository.insert(card)
        }
    }
}
