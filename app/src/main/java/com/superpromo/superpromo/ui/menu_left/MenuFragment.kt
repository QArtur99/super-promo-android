package com.superpromo.superpromo.ui.menu_left

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import com.superpromo.superpromo.GlideApp
import com.superpromo.superpromo.R
import com.superpromo.superpromo.databinding.DrawerFragmentMenuBinding
import com.superpromo.superpromo.databinding.DrawerFragmentOfferFilterBinding
import com.superpromo.superpromo.repository.state.State
import com.superpromo.superpromo.ui.menu_filter.adapter.FilterShopListAdapter
import com.superpromo.superpromo.ui.main.SharedShopVm
import com.superpromo.superpromo.ui.menu_filter.FilterOfferViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MenuFragment : Fragment() {

    private val sharedShopVm: SharedShopVm by viewModels({ requireActivity() })
    private val filterOfferViewModel: FilterOfferViewModel by viewModels()
    private lateinit var binding: DrawerFragmentMenuBinding
    private lateinit var adapter: FilterShopListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DrawerFragmentMenuBinding.inflate(inflater)

        initQuery()
        initAdapter()
        initSwipeToRefresh()

        observeShops()

        return binding.root
    }

    private fun observeShops() {
        sharedShopVm.shops.observe(viewLifecycleOwner, {
            when (it.state) {
                is State.Loading -> {
                    binding.swipeRefresh.isRefreshing = true
                    binding.noConnection.noConnection.visibility = View.GONE
                    binding.emptyView.emptyView.visibility = View.GONE
                }
                is State.Success -> {
                    adapter.submitList(it.shopList)
                    binding.swipeRefresh.isRefreshing = false
                    if (it.shopList.isEmpty()) {
                        binding.noConnection.noConnection.visibility = View.GONE
                        binding.emptyView.emptyView.visibility = View.VISIBLE
                    } else {
                        binding.noConnection.noConnection.visibility = View.GONE
                        binding.emptyView.emptyView.visibility = View.GONE
                    }
                }
                is State.Error -> {
                    binding.swipeRefresh.isRefreshing = false
                    if (it.shopList.isEmpty()) {
                        binding.noConnection.noConnection.visibility = View.VISIBLE
                        binding.emptyView.emptyView.visibility = View.GONE
                    } else {
                        binding.noConnection.noConnection.visibility = View.GONE
                        binding.emptyView.emptyView.visibility = View.GONE
                    }
                }
            }
        })
    }

    private fun initQuery() {
//        binding.appBar.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
//            override fun onQueryTextChange(newText: String?): Boolean {
//                newText?.let { sharedShopVm.showShops(newText) }
//                return true
//            }
//
//            override fun onQueryTextSubmit(query: String?): Boolean {
//                query?.let { sharedShopVm.showShops(query) }
//                return true
//            }
//        })
    }

    private fun initAdapter() {
        val glide = GlideApp.with(this)
        adapter = FilterShopListAdapter(glide, onShopClickListener())
        binding.recyclerView.addItemDecoration(
            DividerItemDecoration(this.context, DividerItemDecoration.VERTICAL)
        )
        binding.recyclerView.adapter = adapter
    }

    private fun initSwipeToRefresh() {
        binding.swipeRefresh.setOnRefreshListener { sharedShopVm.getShops() }
    }

    private fun onShopClickListener() = FilterShopListAdapter.OnClickListener { view, shop ->
        val checkbox = view.findViewById<CheckBox>(R.id.checkbox)
        if (checkbox.isChecked) {
            checkbox.isChecked = false
            sharedShopVm.insertShop(shop.apply { isAvailableInDb = false })
        } else {
            checkbox.isChecked = true
            sharedShopVm.insertShop(shop.apply { isAvailableInDb = true })
        }
    }
}