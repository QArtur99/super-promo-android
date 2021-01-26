package com.superpromo.superpromo.ui.data

import android.os.Parcelable
import com.superpromo.superpromo.data.network.model.Suggestion
import com.superpromo.superpromo.repository.state.State
import kotlinx.parcelize.Parcelize

@Parcelize
class SuggestionModel(
    var suggestionList: List<Suggestion>,
    var filterByName: String,
    var state: State,
) : Parcelable