package com.superpromo.superpromo.ui.compare.fromMain

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.recyclerview.widget.DividerItemDecoration
import com.superpromo.superpromo.GlideApp
import com.superpromo.superpromo.databinding.FragmentCompareBinding
import com.superpromo.superpromo.ui.compare.CompareViewModel
import com.superpromo.superpromo.ui.compare.adapter.fromMain.ProductFromMainPagingAdapter
import com.superpromo.superpromo.ui.compare.adapter.fromMain.ProductFromMainStateAdapter
import com.superpromo.superpromo.ui.util.ext.setNavigationResult
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.*

@AndroidEntryPoint
class CompareFromMainFragment : Fragment() {

    companion object {
        const val KEY_SHOP_ID = "shopId"
    }

    private val compareViewModel: CompareViewModel by viewModels()
    private lateinit var binding: FragmentCompareBinding
    private lateinit var adapter: ProductFromMainPagingAdapter
    private val bundle: CompareFromMainFragmentArgs by navArgs()
    private var loading = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCompareBinding.inflate(inflater)
        initAdapter()
        initSwipeToRefresh()
        initQuerySuggestion()

        bundle.shopId?.let {
            compareViewModel.showShop(it.toInt())
        }
        bundle.query?.let {
            binding.appBar.searchView.setQuery(it, false)
            compareViewModel.showProducts(it)
        }
        setHasOptionsMenu(true);
        return binding.root
    }

    private fun initQuerySuggestion() {
        binding.appBar.searchView.setOnQueryTextFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                val txt = binding.appBar.searchView.query.toString()
                val bundle = bundleOf(SuggestionFromMainFragment.KEY_QUERY to txt)
                setNavigationResult(bundle)
            }
        }
    }

    private fun initAdapter() {
        val glide = GlideApp.with(this)
        adapter = ProductFromMainPagingAdapter(glide, onProductClickListener())
        binding.recyclerView.addItemDecoration(
            DividerItemDecoration(this.context, DividerItemDecoration.VERTICAL)
        )
        binding.recyclerView.adapter = adapter.withLoadStateHeaderAndFooter(
            header = ProductFromMainStateAdapter(adapter),
            footer = ProductFromMainStateAdapter(adapter)
        )

        lifecycleScope.launchWhenCreated {
            adapter.loadStateFlow.collectLatest { loadStates ->
                binding.swipeRefresh.isRefreshing = loadStates.refresh is LoadState.Loading
                handleError(loadStates)
            }
        }
        lifecycleScope.launchWhenCreated {
            compareViewModel.posts.collectLatest {
                adapter.submitData(it)
            }
        }
        lifecycleScope.launchWhenCreated {
            adapter.loadStateFlow
                .distinctUntilChangedBy { it.refresh }
                .filter { it.refresh is LoadState.NotLoading }
                .collect { binding.recyclerView.scrollToPosition(0) }
        }
    }

    private fun handleError(loadStates: CombinedLoadStates) {
        when (loadStates.refresh) {
            is LoadState.Error -> {
                val stateError = loadStates.refresh as LoadState.Error
                if (adapter.itemCount > 0) {
                    binding.emptyView.emptyView.visibility = View.GONE
                    binding.noConnection.noConnection.visibility = View.GONE
                } else {
                    binding.emptyView.emptyView.visibility = View.GONE
                    binding.noConnection.noConnection.visibility = View.VISIBLE
                }
            }
            is LoadState.NotLoading -> {
                if (adapter.itemCount == 0) {
                    binding.emptyView.emptyView.visibility = View.VISIBLE
                    binding.noConnection.noConnection.visibility = View.GONE
                } else {
                    binding.emptyView.emptyView.visibility = View.GONE
                    binding.noConnection.noConnection.visibility = View.GONE
                }
                loading = false
            }
            else -> {
                loading = true
            }
        }
    }

    private fun onProductClickListener() =
        ProductFromMainPagingAdapter.OnClickListener { view, product ->

        }

    private fun initSwipeToRefresh() {
        binding.swipeRefresh.setOnRefreshListener { adapter.refresh() }
    }

}