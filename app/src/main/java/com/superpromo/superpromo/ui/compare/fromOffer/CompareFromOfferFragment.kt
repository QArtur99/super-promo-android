package com.superpromo.superpromo.ui.compare.fromOffer

import android.app.Dialog
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
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.superpromo.superpromo.GlideApp
import com.superpromo.superpromo.GlideRequests
import com.superpromo.superpromo.R
import com.superpromo.superpromo.data.network.model.Product
import com.superpromo.superpromo.databinding.DialogTextListBinding
import com.superpromo.superpromo.databinding.FragmentCompareBinding
import com.superpromo.superpromo.ui.compare.CompareViewModel
import com.superpromo.superpromo.ui.compare.adapter.fromOffer.ProductFromOfferPagingAdapter
import com.superpromo.superpromo.ui.compare.adapter.fromOffer.ProductFromOfferStateAdapter
import com.superpromo.superpromo.ui.compare.fromMain.SuggestionFromMainFragment
import com.superpromo.superpromo.ui.detail.DetailFragment
import com.superpromo.superpromo.ui.shopping.list.adapter_dialog.ShoppingListListAdapter
import com.superpromo.superpromo.ui.util.ext.onNavBackStackListener
import com.superpromo.superpromo.ui.util.ext.setToolbar
import com.superpromo.superpromo.ui.util.ext.snackbarLong
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.filter

@AndroidEntryPoint
class CompareFromOfferFragment : Fragment() {

    companion object {
        const val KEY_SHOP_ID = "shopId"
    }

    private val glide: GlideRequests by lazy { GlideApp.with(this) }
    private val compareViewModel: CompareViewModel by viewModels()

    private lateinit var binding: FragmentCompareBinding
    private lateinit var adapter: ProductFromOfferPagingAdapter
    private val bundle: CompareFromOfferFragmentArgs by navArgs()
    private var showEmpty = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCompareBinding.inflate(inflater)
        setToolbar(binding.appBar.toolbar)
        onNavigationResult()

        initAdapter()
        initSwipeToRefresh()
        initQuerySuggestion()

        bundle.shopId?.let {
            compareViewModel.showShop(it.toInt())
        }
        setHasOptionsMenu(true)
        return binding.root
    }

    private fun onNavigationResult() {
        onNavBackStackListener {
            if (it.containsKey(SuggestionFromMainFragment.KEY_QUERY)) {
                val query = it.get(SuggestionFromMainFragment.KEY_QUERY) as String
                binding.appBar.searchView.setQuery(query, false)
                compareViewModel.showProducts(query)
            }
        }
    }

    private fun initQuerySuggestion() {
        binding.appBar.searchView.setOnQueryTextFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                val txt = binding.appBar.searchView.query.toString()
                val bundle = bundleOf(SuggestionFromMainFragment.KEY_QUERY to txt)
                findNavController().navigate(
                    R.id.action_compare_to_suggestion_product_from_offer,
                    bundle
                )
            }
        }
    }

    private fun initAdapter() {
        adapter = ProductFromOfferPagingAdapter(glide, onProductClickListener())
        binding.recyclerView.addItemDecoration(
            DividerItemDecoration(this.context, DividerItemDecoration.VERTICAL)
        )
        binding.recyclerView.adapter = adapter.withLoadStateHeaderAndFooter(
            header = ProductFromOfferStateAdapter(adapter),
            footer = ProductFromOfferStateAdapter(adapter)
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

    private fun onProductClickListener() = object : ProductFromOfferPagingAdapter.OnClickListener {
        override fun onClick(v: View, product: Product) {
            val bundle = bundleOf(
                DetailFragment.KEY_PRODUCT to product,
            )
            findNavController().navigate(R.id.action_to_detail, bundle)
        }

        override fun onLongClick(v: View, product: Product) {
            createDialog(product)
        }
    }

    private fun createDialog(product: Product) {
        val bindingDialog = DialogTextListBinding.inflate(layoutInflater)
        bindingDialog.appBar.toolbarTitle.text = getString(R.string.shopping_dialog_choose_title)
        val dialog = MaterialAlertDialogBuilder(requireContext())
            .setView(bindingDialog.root)
            .create()
        initShoppingListAdapter(bindingDialog, dialog, product)
        dialog.show()
    }

    private fun initShoppingListAdapter(
        binding: DialogTextListBinding,
        dialog: Dialog,
        product: Product
    ) {
        val adapter = ShoppingListListAdapter(glide, onShoppingListClick(dialog, product))
        binding.recyclerView.adapter = adapter
        binding.recyclerView.addItemDecoration(
            DividerItemDecoration(this.context, DividerItemDecoration.VERTICAL)
        )
        compareViewModel.shoppingLists.observe(
            viewLifecycleOwner,
            {
                adapter.submitList(it)
            }
        )
    }

    private fun onShoppingListClick(dialog: Dialog, product: Product) =
        ShoppingListListAdapter.OnClickListener { v, item ->
            dialog.hide()
            val item = item.copy(
                productCount = item.productCount.inc()
            )
            compareViewModel.updateProductDb(product, item.id)
            compareViewModel.updateShoppingListDb(item)
            snackbarLong("Dodano")
        }

    private fun initSwipeToRefresh() {
        binding.swipeRefresh.setOnRefreshListener { adapter.refresh() }
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        menu.clear()
    }
}
