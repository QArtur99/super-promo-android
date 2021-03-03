package com.superpromo.superpromo.ui.shopping.product.list_active

import android.os.Bundle
import android.view.*
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.superpromo.superpromo.GlideApp
import com.superpromo.superpromo.R
import com.superpromo.superpromo.data.db.model.ProductDb
import com.superpromo.superpromo.data.db.model.ShoppingListDb
import com.superpromo.superpromo.databinding.DialogShoppingListNameBinding
import com.superpromo.superpromo.databinding.FragmentShoppingProductBinding
import com.superpromo.superpromo.ui.main.SharedDrawerVm
import com.superpromo.superpromo.ui.main.SharedShopVm
import com.superpromo.superpromo.ui.shopping.product.detail.ProductDetailFragment
import com.superpromo.superpromo.ui.shopping.product.list_active.adapter.ProductListAdapter
import com.superpromo.superpromo.ui.util.ext.safeNavigate
import com.superpromo.superpromo.ui.util.ext.setToolbar
import com.superpromo.superpromo.ui.util.ext.snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductFragment : Fragment() {

    companion object {
        const val KEY_SHOPPING_LIST = "shoppingList"
    }

    private val sharedShopVm: SharedShopVm by viewModels({ requireActivity() })
    private val sharedDrawerVm: SharedDrawerVm by viewModels({ requireActivity() })
    private val productViewModel: ProductViewModel by viewModels()
    private val bundle: ProductFragmentArgs by navArgs()

    private lateinit var binding: FragmentShoppingProductBinding
    private lateinit var adapter: ProductListAdapter
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
        productViewModel.setShoppingListId(shoppingListDb.id)
    }

    private fun observeMenuList() {
        productViewModel.productList.observe(viewLifecycleOwner, {
            binding.swipeRefresh.isRefreshing = false
            adapter.submitList(it)
            setEmptyView(it)
        })
    }

    private fun setEmptyView(list: List<ProductDb>) {
        binding.emptyView.emptyView.visibility = if (list.isEmpty()) View.VISIBLE else View.GONE
        binding.emptyView.emptyTitleText.text = getString(R.string.product_empty_list)
        binding.emptyView.emptySubtitleText.text = getString(R.string.product_empty_sub_text)
        binding.emptyView.emptyImage.setImageResource(R.drawable.gradient_ic_baseline_add_shopping_cart_24)
    }

    private fun initAdapter() {
        val glide = GlideApp.with(this)
        adapter = ProductListAdapter(glide, onShopClickListener())
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

    private fun onShopClickListener() = object : ProductListAdapter.OnClickListener {
        override fun onClick(v: View, productDb: ProductDb) {
            val bundle = bundleOf(
                ProductDetailFragment.KEY_PRODUCT to productDb,
            )
            safeNavigate(R.id.action_to_product_detail, bundle)
        }

        override fun onSelect(v: View, productDb: ProductDb) {
            productDb.isSelected = productDb.isSelected.not()
            productViewModel.updateProductDb(productDb)
        }
    }

    private fun editListName() {
        val bindingDialog = DialogShoppingListNameBinding.inflate(layoutInflater)
        bindingDialog.editText.setText(shoppingListDb.name)
        val dialog = MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.shopping_list_dialog_name_title)
            .setView(bindingDialog.root)
            .setPositiveButton(R.string.common_btn_ok) { _, _ ->
                onEditListSuccess(bindingDialog)
            }
            .setNegativeButton(R.string.common_btn_cancel) { _, _ ->
            }
            .create()
        dialog.show()
    }

    private fun onEditListSuccess(bindingDialog: DialogShoppingListNameBinding) {
        val name = bindingDialog.editText.text.toString()
        shoppingListDb = shoppingListDb.copy(name = name)
        productViewModel.updateShoppingListDb(shoppingListDb)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.shopping_list_product, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_add -> onAdd()
            R.id.action_search -> onSearch()
            R.id.action_edit -> onEdit()
            R.id.action_archive -> onArchive()
            R.id.action_delete -> onDelete()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun onAdd() {
        val bindingDialog = DialogShoppingListNameBinding.inflate(layoutInflater)
        val dialog = MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.product_dialog_name_title)
            .setView(bindingDialog.root)
            .setPositiveButton(R.string.common_btn_ok) { _, _ ->
                onAddSuccess(bindingDialog)
            }
            .setNegativeButton(R.string.common_btn_cancel) { _, _ ->
            }
            .create()
        dialog.show()
    }

    private fun onAddSuccess(bindingDialog: DialogShoppingListNameBinding) {
        val name = bindingDialog.editText.text.toString()
        val productDb = ProductDb(
            name = name,
            shoppingListId = shoppingListDb.id,
            isLocal = true
        )
        productViewModel.updateProductDb(productDb)
    }

    private fun onSearch() {
        sharedShopVm.showShops("")
        findNavController().navigate(R.id.action_to_offer)
    }


    private fun onEdit() {
        editListName()
    }

    private fun onArchive() {
        shoppingListDb = shoppingListDb.copy(isArchived = true)
        productViewModel.updateShoppingListDb(shoppingListDb)
        snackbar(binding.root, R.string.shopping_list_moved_to_archive)
        activity?.onBackPressed()
    }

    private fun onDelete() {
        productViewModel.deleteShoppingListDb(shoppingListDb)
        snackbar(binding.root, R.string.shopping_list_deleted)
        activity?.onBackPressed()
    }
}