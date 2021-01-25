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
import com.superpromo.superpromo.ui.compare.fromMain.CompareFromMainFragment
import com.superpromo.superpromo.ui.main.SharedProductVm
import com.superpromo.superpromo.ui.offer.adapter.ShopListAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OfferFragment : Fragment() {

    private val sharedProductVm: SharedProductVm by viewModels({ requireActivity() })
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
        sharedProductVm.shops.observe(viewLifecycleOwner, {
            adapter.submitList(it)
            binding.swipeRefresh.isRefreshing = false
        })

        return binding.root
    }

    private fun initQuery() {
        binding.appBar.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let { sharedProductVm.showShops(newText) }
                return true
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let { sharedProductVm.showShops(query) }
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
        binding.swipeRefresh.setOnRefreshListener { sharedProductVm.getShops() }
    }

    private fun onShopClickListener() = ShopListAdapter.OnClickListener { view, product ->
        val bundle = bundleOf(
            CompareFromMainFragment.KEY_SHOP_ID to product.id.toString(),
        )
        findNavController().navigate(R.id.action_offer_to_compare_from_offer, bundle)
    }
}