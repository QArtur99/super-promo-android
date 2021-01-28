package com.superpromo.superpromo.ui.main

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.superpromo.superpromo.data.network.model.Suggestion
import com.superpromo.superpromo.repository.main.SuperPromoRepository
import com.superpromo.superpromo.repository.state.ResultApi
import com.superpromo.superpromo.repository.state.State
import com.superpromo.superpromo.ui.data.SuggestionModel
import com.superpromo.superpromo.ui.util.ext.addSourceInvoke
import com.superpromo.superpromo.ui.util.unaccent
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

    private val _suggestionErrorState = MutableLiveData<State>(State.Loading)
    private val suggestionErrorState: LiveData<State> = _suggestionErrorState
    private val _suggestionFull = savedStateHandle.getLiveData<List<Suggestion>>(KEY_SUGGESTIONS)
    private val suggestionFull = _suggestionFull.map { it }
    private val _filterByShopName = savedStateHandle.getLiveData<String>(KEY_FILTER_SUGGESTIONS)
    private val filterByShopName = _filterByShopName.map { it }
    val suggestions = MediatorLiveData<SuggestionModel>().apply {
        value = SuggestionModel(emptyList(), "", State.Loading)
        addSourceInvoke(suggestionErrorState) { value?.state = it }
        addSourceInvoke(suggestionFull) { value?.suggestionList = it }
        addSourceInvoke(filterByShopName) { name ->
            value?.suggestionList = suggestionFull.value?.filter {
                it.name.unaccent().contains(name.unaccent(), ignoreCase = true)
            } ?: emptyList()
        }
    }

    init {
        savedStateHandle.set(KEY_SUGGESTIONS, emptyList<Suggestion>())
        savedStateHandle.set(KEY_FILTER_SUGGESTIONS, "")
        getProductSuggestions()
    }

    fun getProductSuggestions() {
        viewModelScope.launch {
            _suggestionErrorState.value = State.Loading
            when (val list = superPromoRepository.getProductSuggestions()) {
                is ResultApi.Success -> {
                    savedStateHandle.set(KEY_SUGGESTIONS, list.data)
                    _suggestionErrorState.value = State.Success
                }
                is ResultApi.Error -> {
                    Timber.e(list.message)
                    _suggestionErrorState.value = State.Error
                }
            }
        }
    }

    fun showSuggestions(query: String) {
        savedStateHandle.set(KEY_FILTER_SUGGESTIONS, query)
    }

}