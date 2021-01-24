package com.superpromo.superpromo.ui.main

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.superpromo.superpromo.data.network.model.Suggestion
import com.superpromo.superpromo.repository.main.SuperPromoRepository
import com.superpromo.superpromo.repository.state.ResultStatus
import kotlinx.coroutines.launch
import timber.log.Timber

class SharedSuggestionVm @ViewModelInject constructor(
    @Assisted private val savedStateHandle: SavedStateHandle,
    private val superPromoRepository: SuperPromoRepository
) : ViewModel() {
    companion object {
        const val KEY_SUGGESTIONS = "suggestionsList"
        const val KEY_FILTER_SUGGESTIONS = "suggestionFilter"
    }

    private val _suggestionFull = savedStateHandle.getLiveData<List<Suggestion>>(KEY_SUGGESTIONS)
    private val suggestionFull = _suggestionFull.map { it }
    private val _filterByShopName = savedStateHandle.getLiveData<String>(KEY_FILTER_SUGGESTIONS)
    private val filterByShopName = _filterByShopName.map { it }
    val suggestions = MediatorLiveData<List<Suggestion>>().apply {
        addSource(suggestionFull) { value = it }
        addSource(filterByShopName) { name ->
            value = suggestionFull.value?.filter {
                it.name.contains(name, ignoreCase = true)
            } ?: return@addSource
        }
    }


    init {
        getProductSuggestions()
        savedStateHandle.set(KEY_SUGGESTIONS, emptyList<Suggestion>())
        savedStateHandle.set(KEY_FILTER_SUGGESTIONS, "")
    }

    fun getProductSuggestions() {
        viewModelScope.launch {
            when (val list = superPromoRepository.getProductSuggestions()) {
                is ResultStatus.Success -> {
                    savedStateHandle.set(KEY_SUGGESTIONS, list.data)
                }
                is ResultStatus.Error -> {
                    Timber.e(list.message)
                }
            }
        }
    }

    fun showSuggestions(query: String) {
        savedStateHandle.set(KEY_FILTER_SUGGESTIONS, query)
    }

}