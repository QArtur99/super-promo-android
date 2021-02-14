package com.superpromo.superpromo.ui.menu_left

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.superpromo.superpromo.GlideApp
import com.superpromo.superpromo.databinding.DrawerFragmentMenuBinding
import com.superpromo.superpromo.ui.main.SharedDrawerVm
import com.superpromo.superpromo.ui.menu_left.adapter.MenuListAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MenuFragment : Fragment() {

    private val sharedDrawerVm: SharedDrawerVm by viewModels({ requireActivity() })
    private val menuViewModel: MenuViewModel by viewModels()

    private lateinit var binding: DrawerFragmentMenuBinding
    private lateinit var adapter: MenuListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DrawerFragmentMenuBinding.inflate(inflater)

        initAdapter()

        observeMenuList()

        return binding.root
    }

    private fun observeMenuList() {
        menuViewModel.menuList.observe(viewLifecycleOwner, {
            adapter.submitList(it)
        })
    }

    private fun initAdapter() {
        val glide = GlideApp.with(this)
        adapter = MenuListAdapter(glide, onShopClickListener())
        binding.recyclerView.adapter = adapter
    }

    private fun onShopClickListener() = MenuListAdapter.OnClickListener { view, shop ->
        sharedDrawerVm.onCloseStartClick()
    }
}