package com.superpromo.superpromo.ui.shopping.list

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.zxing.integration.android.IntentIntegrator
import com.superpromo.superpromo.GlideApp
import com.superpromo.superpromo.R
import com.superpromo.superpromo.data.db.model.CardDb
import com.superpromo.superpromo.data.db.model.ShoppingListDb
import com.superpromo.superpromo.databinding.DialogShoppingListNameBinding
import com.superpromo.superpromo.databinding.FragmentShoppingListBinding
import com.superpromo.superpromo.ui.card_add.CardAddFragment
import com.superpromo.superpromo.ui.main.SharedDrawerVm
import com.superpromo.superpromo.ui.shopping.list.adapter.ShoppingListListAdapter
import com.superpromo.superpromo.ui.shopping.product.list_active.ProductFragment
import com.superpromo.superpromo.ui.util.ext.setToolbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ShoppingListFragment : Fragment() {
    private val sharedDrawerVm: SharedDrawerVm by viewModels({ requireActivity() })
    private val shoppingListViewModel: ShoppingListViewModel by viewModels()

    private lateinit var binding: FragmentShoppingListBinding
    private lateinit var adapter: ShoppingListListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentShoppingListBinding.inflate(inflater)
        binding.appBar.toolbarTitle.text = getText(R.string.main_shopping)
        setToolbar(binding.appBar.toolbar)

        initAdapter()
        initSwipeToRefresh()

        observeMenuList()

        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            if (result.contents != null) {
                val cardDb = CardDb(0, "", "", result.contents, result.formatName)
                val bundle = bundleOf(
                    CardAddFragment.KEY_CARD to cardDb,
                )
                findNavController().navigate(R.id.action_to_card_add, bundle)
            }
        }
    }

    private fun observeMenuList() {
        shoppingListViewModel.shoppingLists.observe(
            viewLifecycleOwner,
            {
                binding.swipeRefresh.isRefreshing = false
                adapter.submitList(it)
                setEmptyView(it)
            }
        )
    }

    private fun setEmptyView(list: List<ShoppingListDb>) {
        binding.emptyView.emptyView.visibility = if (1 >= list.size) View.VISIBLE else View.GONE
        binding.emptyView.emptyTitleText.text = getString(R.string.shopping_list_empty_list)
        binding.emptyView.emptySubtitleText.text = getString(R.string.shopping_list_empty_sub_text)
        binding.emptyView.emptyImage.setImageResource(R.drawable.gradient_ic_baseline_add_shopping_cart_24)
    }

    private fun initAdapter() {
        val glide = GlideApp.with(this)
        adapter = ShoppingListListAdapter(glide, onShopClickListener())
        binding.recyclerView.adapter = adapter
    }

    private fun initSwipeToRefresh() {
        binding.swipeRefresh.setOnRefreshListener {
            binding.swipeRefresh.isRefreshing = false
        }
    }

    private fun onShopClickListener() =
        ShoppingListListAdapter.OnClickListener { view, shoppingListDb ->
            when (shoppingListDb.name) {
                "" -> addList()
                else -> {
                    val bundle = bundleOf(
                        ProductFragment.KEY_SHOPPING_LIST to shoppingListDb,
                    )
                    findNavController().navigate(R.id.action_to_product, bundle)
                }
            }
        }

    private fun addList() {
        val bindingDialog = DialogShoppingListNameBinding.inflate(layoutInflater)
        val dialog = MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.shopping_list_dialog_name_title)
            .setView(bindingDialog.root)
            .setPositiveButton(R.string.common_btn_ok) { _, _ ->
                onAddListSuccess(bindingDialog)
            }
            .setNegativeButton(R.string.common_btn_cancel) { _, _ ->
            }
            .create()
        dialog.show()
    }

    private fun onAddListSuccess(bindingDialog: DialogShoppingListNameBinding) {
        val name = bindingDialog.editText.text.toString()
        val shoppingList = ShoppingListDb(name = name)
        shoppingListViewModel.addShoppingListDb(shoppingList)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.shopping_list, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_archive -> onArchive()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun onArchive() {
        findNavController().navigate(R.id.action_to_archive)
    }
}
