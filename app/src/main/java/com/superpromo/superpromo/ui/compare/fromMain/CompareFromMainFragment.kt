package com.superpromo.superpromo.ui.compare.fromMain

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.recyclerview.widget.DividerItemDecoration
import com.superpromo.superpromo.GlideApp
import com.superpromo.superpromo.GlideRequests
import com.superpromo.superpromo.R
import com.superpromo.superpromo.data.network.model.Product
import com.superpromo.superpromo.databinding.FragmentCompareBinding
import com.superpromo.superpromo.ui.compare.CompareViewModel
import com.superpromo.superpromo.ui.compare.adapter.fromMain.ProductFromMainPagingAdapter
import com.superpromo.superpromo.ui.compare.adapter.fromMain.ProductFromMainStateAdapter
import com.superpromo.superpromo.ui.detail.DetailFragment
import com.superpromo.superpromo.ui.main.SharedShopVm
import com.superpromo.superpromo.ui.util.ext.setNavigationResult
import com.superpromo.superpromo.ui.util.ext.setToolbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.filter

@AndroidEntryPoint
class CompareFromMainFragment : Fragment() {

    private val glide: GlideRequests by lazy { GlideApp.with(this) }
    private val sharedShopVm: SharedShopVm by viewModels({ requireActivity() })
    private val compareViewModel: CompareViewModel by viewModels()

    private lateinit var binding: FragmentCompareBinding
    private lateinit var adapter: ProductFromMainPagingAdapter
    private val bundle: CompareFromMainFragmentArgs by navArgs()
    private var showEmpty = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCompareBinding.inflate(inflater)
        setToolbar(binding.appBar.toolbar)

        initAdapter()
        initSwipeToRefresh()
        initQuerySuggestion()

        bundle.query?.let { query ->
            sharedShopVm.shopsAvailable.observe(
                viewLifecycleOwner,
                {
                    binding.appBar.searchView.setQuery(query, false)
                    compareViewModel.showProducts(it, query)
                }
            )
        }
        setHasOptionsMenu(true)
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
            is LoadState.Loading -> {
                binding.emptyView.emptyView.visibility = View.GONE
                binding.noConnection.noConnection.visibility = View.GONE
            }
            is LoadState.NotLoading -> {
                if (adapter.itemCount == 0 && showEmpty) {
                    binding.emptyView.emptyView.visibility = View.VISIBLE
                    binding.noConnection.noConnection.visibility = View.GONE
                } else {
                    binding.emptyView.emptyView.visibility = View.GONE
                    binding.noConnection.noConnection.visibility = View.GONE
                }
                showEmpty = true
            }
            is LoadState.Error -> {
                val stateError = loadStates.refresh as LoadState.Error
                if (adapter.itemCount == 0) {
                    binding.emptyView.emptyView.visibility = View.GONE
                    binding.noConnection.noConnection.visibility = View.VISIBLE
                } else {
                    binding.emptyView.emptyView.visibility = View.GONE
                    binding.noConnection.noConnection.visibility = View.GONE
                }
            }
        }
    }

    private fun onProductClickListener() = object : ProductFromMainPagingAdapter.OnClickListener {
        override fun onClick(v: View, product: Product) {
            val bundle = bundleOf(
                DetailFragment.KEY_PRODUCT to product,
            )
            findNavController().navigate(R.id.action_to_detail, bundle)
        }

        override fun onLongClick(v: View, product: Product) {
            TODO("Not yet implemented")
        }
    }

    private fun initSwipeToRefresh() {
        binding.swipeRefresh.setOnRefreshListener { adapter.refresh() }
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        menu.clear()
    }
}
