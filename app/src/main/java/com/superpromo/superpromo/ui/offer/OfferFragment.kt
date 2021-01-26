package com.superpromo.superpromo.ui.offer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.superpromo.superpromo.GlideApp
import com.superpromo.superpromo.R
import com.superpromo.superpromo.databinding.FragmentCompareBinding
import com.superpromo.superpromo.repository.state.State
import com.superpromo.superpromo.ui.compare.fromMain.CompareFromMainFragment
import com.superpromo.superpromo.ui.main.SharedShopVm
import com.superpromo.superpromo.ui.offer.adapter.ShopListAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OfferFragment : Fragment() {

    private val sharedShopVm: SharedShopVm by viewModels({ requireActivity() })
    private val offerViewModel: OfferViewModel by viewModels()
    private lateinit var binding: FragmentCompareBinding
    private lateinit var adapter: ShopListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCompareBinding.inflate(inflater)

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
        binding.appBar.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let { sharedShopVm.showShops(newText) }
                return true
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let { sharedShopVm.showShops(query) }
                return true
            }
        })
    }

    private fun initAdapter() {
        val glide = GlideApp.with(this)
        adapter = ShopListAdapter(glide, onShopClickListener())
        binding.recyclerView.addItemDecoration(
            DividerItemDecoration(this.context, DividerItemDecoration.VERTICAL)
        )
        binding.recyclerView.adapter = adapter
    }

    private fun initSwipeToRefresh() {
        binding.swipeRefresh.setOnRefreshListener { sharedShopVm.getShops() }
    }

    private fun onShopClickListener() = ShopListAdapter.OnClickListener { view, product ->
        val bundle = bundleOf(
            CompareFromMainFragment.KEY_SHOP_ID to product.id.toString(),
        )
        findNavController().navigate(R.id.action_offer_to_compare_from_offer, bundle)
    }
}