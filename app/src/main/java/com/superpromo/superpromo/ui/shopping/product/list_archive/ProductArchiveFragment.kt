package com.superpromo.superpromo.ui.shopping.product.list_archive

import android.os.Bundle
import android.view.*
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import com.superpromo.superpromo.GlideApp
import com.superpromo.superpromo.R
import com.superpromo.superpromo.data.db.model.ProductDb
import com.superpromo.superpromo.data.db.model.ShoppingListDb
import com.superpromo.superpromo.databinding.FragmentShoppingProductBinding
import com.superpromo.superpromo.ui.main.SharedDrawerVm
import com.superpromo.superpromo.ui.shopping.product.detail_archive.ProductArchiveDetailFragment
import com.superpromo.superpromo.ui.shopping.product.list_active.ProductFragmentArgs
import com.superpromo.superpromo.ui.shopping.product.list_archive.adapter.ProductArchiveListAdapter
import com.superpromo.superpromo.ui.util.ext.setToolbar
import com.superpromo.superpromo.ui.util.ext.snackbarLong
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductArchiveFragment : Fragment() {

    companion object {
        const val KEY_SHOPPING_LIST = "shoppingList"
    }

    private val sharedDrawerVm: SharedDrawerVm by viewModels({ requireActivity() })
    private val productArchiveViewModel: ProductArchiveViewModel by viewModels()
    private val bundle: ProductFragmentArgs by navArgs()

    private lateinit var binding: FragmentShoppingProductBinding
    private lateinit var adapter: ProductArchiveListAdapter
    private lateinit var shoppingListDb: ShoppingListDb

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentShoppingProductBinding.inflate(inflater)
        setToolbar(binding.appBar.toolbar)

        getBundle()

        initAdapter()
        initSwipeToRefresh()

        observeMenuList()

        setHasOptionsMenu(true)
        return binding.root
    }

    private fun getBundle() {
        bundle.shoppingList?.let {
            setShoppingList(it)
        }
    }

    private fun setShoppingList(shoppingListDb: ShoppingListDb) {
        this.shoppingListDb = shoppingListDb
        productArchiveViewModel.setShoppingListId(shoppingListDb.id)
    }

    private fun observeMenuList() {
        productArchiveViewModel.productList.observe(
            viewLifecycleOwner,
            {
                binding.swipeRefresh.isRefreshing = false
                adapter.submitList(it)
                setEmptyView(it)
            }
        )
    }

    private fun setEmptyView(list: List<ProductDb>) {
        binding.emptyView.emptyView.visibility = if (list.isEmpty()) View.VISIBLE else View.GONE
        binding.emptyView.emptyTitleText.text = getString(R.string.product_empty_list)
        binding.emptyView.emptySubtitleText.text = getString(R.string.product_empty_sub_text)
        binding.emptyView.emptyImage.setImageResource(R.drawable.gradient_ic_baseline_add_shopping_cart_24)
    }

    private fun initAdapter() {
        val glide = GlideApp.with(this)
        adapter = ProductArchiveListAdapter(glide, onShopClickListener())
        binding.recyclerView.adapter = adapter
        binding.recyclerView.addItemDecoration(
            DividerItemDecoration(this.context, DividerItemDecoration.VERTICAL)
        )
    }

    private fun initSwipeToRefresh() {
        binding.swipeRefresh.setOnRefreshListener {
            binding.swipeRefresh.isRefreshing = false
        }
    }

    private fun onShopClickListener() = object : ProductArchiveListAdapter.OnClickListener {
        override fun onClick(v: View, productDb: ProductDb) {
            val bundle = bundleOf(
                ProductArchiveDetailFragment.KEY_PRODUCT to productDb,
            )
            findNavController().navigate(R.id.action_to_product_archive_detail, bundle)
        }

        override fun onSelect(v: View, productDb: ProductDb) {
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.shopping_list_product_archive, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_unarchive -> onUnarchive()
            R.id.action_delete -> onDelete()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun onUnarchive() {
        shoppingListDb = shoppingListDb.copy(isArchived = false)
        productArchiveViewModel.updateShoppingListDb(shoppingListDb)
        snackbarLong(R.string.shopping_list_moved_to_active)
        activity?.onBackPressed()
    }

    private fun onDelete() {
        productArchiveViewModel.deleteShoppingListDb(shoppingListDb)
        snackbarLong(R.string.shopping_list_deleted)
        activity?.onBackPressed()
    }
}
