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

    private val _onCloseEndClick = MutableLiveData<Event<Boolean>>()
    val onCloseEndClick: LiveData<Event<Boolean>> = _onCloseEndClick

    private val _onOpenedEnd = MutableLiveData<Event<Boolean>>()
    val onOpenedEnd: LiveData<Event<Boolean>> = _onOpenedEnd

    fun onCloseEndClick() {
        _onCloseEndClick.value = Event(true)
    }

    fun onOpenedEnd() {
        _onOpenedEnd.value = Event(true)
    }


}