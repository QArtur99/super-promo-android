package com.superpromo.superpromo.ui.menu_left

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.superpromo.superpromo.R
import com.superpromo.superpromo.repository.main.SuperPromoRepository
import com.superpromo.superpromo.ui.data.MenuModel
import dagger.assisted.Assisted
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MenuViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val superPromoRepository: SuperPromoRepository
) : ViewModel() {

    private val _menuList = MutableLiveData<List<MenuModel>>()
    val menuList: LiveData<List<MenuModel>> = _menuList

    init {
        _menuList.value = listOf(
            MenuModel(R.drawable.ic_baseline_shopping_cart_24, R.string.menu_shopping),
            MenuModel(R.drawable.ic_baseline_credit_card_24, R.string.menu_cards),
            MenuModel(R.drawable.ic_baseline_favorite_24, R.string.menu_favorite),
            MenuModel(R.drawable.ic_baseline_share_24, R.string.menu_share),
            MenuModel(R.drawable.ic_baseline_email_24, R.string.menu_contact),
            MenuModel(R.drawable.ic_baseline_settings_24, R.string.menu_settings),
            MenuModel(R.drawable.ic_baseline_info_24, R.string.menu_info),
        )
    }
}
