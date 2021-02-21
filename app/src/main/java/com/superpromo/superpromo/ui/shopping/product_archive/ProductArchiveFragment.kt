package com.superpromo.superpromo.ui.shopping.product_archive

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.superpromo.superpromo.GlideApp
import com.superpromo.superpromo.R
import com.superpromo.superpromo.data.db.model.ShoppingListDb
import com.superpromo.superpromo.databinding.DialogShoppingListNameBinding
import com.superpromo.superpromo.databinding.FragmentProductBinding
import com.superpromo.superpromo.ui.card_detail.CardDetailFragment
import com.superpromo.superpromo.ui.main.SharedDrawerVm
import com.superpromo.superpromo.ui.shopping.product.ProductFragmentArgs
import com.superpromo.superpromo.ui.shopping.product.adapter.ProductListAdapter
import com.superpromo.superpromo.ui.util.ext.setToolbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductArchiveFragment : Fragment() {

    companion object {
        const val KEY_SHOPPING_LIST = "shoppingList"
    }

    private val sharedDrawerVm: SharedDrawerVm by viewModels({ requireActivity() })
    private val productArchiveViewModel: ProductArchiveViewModel by viewModels()
    private val bundle: ProductFragmentArgs by navArgs()

    private lateinit var binding: FragmentProductBinding
    private lateinit var adapter: ProductListAdapter
    private lateinit var shoppingListDb: ShoppingListDb

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProductBinding.inflate(inflater)
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
        productArchiveViewModel.productList.observe(viewLifecycleOwner, {
            binding.swipeRefresh.isRefreshing = false
            adapter.submitList(it)
        })
    }

    private fun initAdapter() {
        val glide = GlideApp.with(this)
        adapter = ProductListAdapter(glide, onShopClickListener())
        binding.recyclerView.adapter = adapter
    }

    private fun initSwipeToRefresh() {
        binding.swipeRefresh.setOnRefreshListener {
            binding.swipeRefresh.isRefreshing = false
        }
    }

    private fun onShopClickListener() = ProductListAdapter.OnClickListener { view, card ->
        val bundle = bundleOf(
            CardDetailFragment.KEY_CARD to card,
        )
        findNavController().navigate(R.id.action_to_card_detail, bundle)
    }

    private fun editListName() {
        val bindingDialog = DialogShoppingListNameBinding.inflate(layoutInflater)
        bindingDialog.editText.setText(shoppingListDb.name)
        val dialog = AlertDialog.Builder(requireContext())
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
        productArchiveViewModel.editShoppingListDb(shoppingListDb)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.shopping_list_product_archive, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_edit -> onEdit()
            R.id.action_unarchive -> onUnarchive()
            R.id.action_delete -> onDelete()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun onEdit() {
        editListName()
    }

    private fun onUnarchive() {
        shoppingListDb = shoppingListDb.copy(isArchived = false)
        productArchiveViewModel.editShoppingListDb(shoppingListDb)
        activity?.onBackPressed()
    }

    private fun onDelete() {
        productArchiveViewModel.deleteShoppingListDb(shoppingListDb)
        activity?.onBackPressed()
    }
}