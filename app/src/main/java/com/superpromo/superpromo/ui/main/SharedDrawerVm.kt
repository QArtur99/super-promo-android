package com.superpromo.superpromo.ui.main

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.superpromo.superpromo.repository.main.SuperPromoRepository
import com.superpromo.superpromo.ui.util.Event

class SharedDrawerVm @ViewModelInject constructor(
    @Assisted private val savedStateHandle: SavedStateHandle,
    private val superPromoRepository: SuperPromoRepository
) : ViewModel() {

    private val _onCloseEndClick = MutableLiveData<Event<Int>>()
    val onCloseEndClick: LiveData<Event<Int>> = _onCloseEndClick

    private val _onOpenedEnd = MutableLiveData<Event<Int>>()
    val onOpenedEnd: LiveData<Event<Int>> = _onOpenedEnd

    private val _onCloseStartClick = MutableLiveData<Event<Int>>()
    val onCloseStartClick: LiveData<Event<Int>> = _onCloseStartClick

    fun onCloseEndClick(id: Int) {
        _onCloseEndClick.value = Event(id)
    }

    fun onCloseStartClick(id: Int) {
        _onCloseStartClick.value = Event(id)
    }

    fun onOpenedEnd(id: Int) {
        _onOpenedEnd.value = Event(id)
    }


}