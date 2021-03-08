package com.superpromo.superpromo.repository.state

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

sealed class State : Parcelable {
    @Parcelize
    object Loading : State()

    @Parcelize
    object Success : State()

    @Parcelize
    object Error : State()
}
