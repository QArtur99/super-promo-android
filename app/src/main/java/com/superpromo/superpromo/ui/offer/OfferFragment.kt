package com.superpromo.superpromo.ui.offer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.paging.LoadState
import androidx.recyclerview.widget.DividerItemDecoration
import com.superpromo.superpromo.GlideApp
import com.superpromo.superpromo.databinding.FragmentCompareBinding
import com.superpromo.superpromo.ui.compare.CompareStateAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OfferFragment : Fragment() {

    private val offerViewModel: OfferViewModel by viewModels()
    private lateinit var binding: FragmentCompareBinding
    private lateinit var adapter: ShopListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCompareBinding.inflate(inflater)

        initAdapter()
        initSwipeToRefresh()
        offerViewModel.shops.observe(viewLifecycleOwner, {
            adapter.submitList(it)
            binding.swipeRefresh.isRefreshing = false
        })

        return binding.root
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
        binding.swipeRefresh.setOnRefreshListener { offerViewModel.getShops() }
    }

    private fun onShopClickListener() = ShopListAdapter.OnClickListener { view, product ->

    }
}