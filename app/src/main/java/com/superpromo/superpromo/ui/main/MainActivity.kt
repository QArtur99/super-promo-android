package com.superpromo.superpromo.ui.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.superpromo.superpromo.R
import com.superpromo.superpromo.ui.util.ext.setStatusBarGradiant
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val sharedShopVm: SharedShopVm by viewModels()
    private val sharedSuggestionVm: SharedSuggestionVm by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        setStatusBarGradiant(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        sharedShopVm.shops.observe(this, {})
        sharedSuggestionVm.suggestions.observe(this, {})
    }
}