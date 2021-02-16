package com.superpromo.superpromo.ui.main

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import com.superpromo.superpromo.R
import com.superpromo.superpromo.databinding.ActivityMainBinding
import com.superpromo.superpromo.ui.util.EventObserver
import com.superpromo.superpromo.ui.util.ext.setStatusBarGradient
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val sharedShopVm: SharedShopVm by viewModels()
    private val sharedSuggestionVm: SharedSuggestionVm by viewModels()
    private val sharedDrawerVm: SharedDrawerVm by viewModels()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_SuperPromo)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setStatusBarGradient(this)
        setContentView(binding.root)
        sharedShopVm.shopList.observe(this, {})
        sharedShopVm.shops.observe(this, {})
        sharedSuggestionVm.suggestions.observe(this, {})
        binding.drawer.addDrawerListener(object : DrawerLayout.DrawerListener {
            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {

            }

            override fun onDrawerOpened(drawerView: View) {
//                setStatusBarDark()
//                sharedDrawerVm.onOpenedEnd()
            }

            override fun onDrawerClosed(drawerView: View) {
//                setStatusBarTransparent()
            }

            override fun onDrawerStateChanged(newState: Int) {
//                TODO("Not yet implemented")
            }
        })
        sharedDrawerVm.onCloseEndClick.observe(this, EventObserver {
            binding.drawer.closeDrawer(GravityCompat.END)
        })
        sharedDrawerVm.onCloseStartClick.observe(this, EventObserver {
            binding.drawer.closeDrawer(GravityCompat.START)
            when (it) {
                R.string.menu_cards -> findNavController().navigate(R.id.action_start_to_card)
            }
        })
    }

    private fun findNavController() = binding.navHostFragment.findNavController()

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.filter, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_favorite -> {
                if (binding.drawer.isDrawerOpen(GravityCompat.END)) {
                    binding.drawer.closeDrawer(GravityCompat.END)
                } else {
                    binding.drawer.openDrawer(GravityCompat.END)
                }
            }
            android.R.id.home -> {
                if (binding.drawer.isDrawerOpen(GravityCompat.START)) {
                    binding.drawer.closeDrawer(GravityCompat.START)
                } else {
                    binding.drawer.openDrawer(GravityCompat.START)
                }
            }
            else -> return super.onOptionsItemSelected(item)
        }
        return true
    }
}