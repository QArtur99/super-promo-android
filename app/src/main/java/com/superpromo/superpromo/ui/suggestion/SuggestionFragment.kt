package com.superpromo.superpromo.ui.suggestion

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import androidx.core.view.doOnAttach
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.superpromo.superpromo.GlideApp
import com.superpromo.superpromo.R
import com.superpromo.superpromo.databinding.FragmentSuggestionBinding
import com.superpromo.superpromo.ui.compare.CompareFragment.Companion.KEY_SHOP_ID
import com.superpromo.superpromo.ui.main.SharedSuggestionVm
import com.superpromo.superpromo.ui.suggestion.suggestion.SuggestionListAdapter
import com.superpromo.superpromo.ui.util.ext.onNavBackStackListener
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SuggestionFragment : Fragment() {

    companion object {
        const val KEY_QUERY = "query"
    }

    private val sharedViewModel: SharedSuggestionVm by viewModels({ requireActivity() })
    private lateinit var binding: FragmentSuggestionBinding
    private lateinit var adapter: SuggestionListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSuggestionBinding.inflate(inflater)
        onNavigationResult()
        initQuery()
        initAdapter()
        initSwipeToRefresh()
        sharedViewModel.suggestions.observe(viewLifecycleOwner, {
            adapter.submitList(it)
            binding.swipeRefresh.isRefreshing = false
        })
        binding.searchView.doOnAttach {
//            binding.searchView.requestFocus()
//            context?.showSoftKeyBoard(binding.searchView)
        }
        setHasOptionsMenu(true);
        return binding.root
    }

    fun onNavigationResult() {
        onNavBackStackListener {
            if (it.containsKey(KEY_QUERY)) {
                val query = it.get(KEY_QUERY) as String
                binding.searchView.setQuery(query, false)
                binding.searchView.requestFocus()
            }
        }
    }

    private fun initQuery() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let { sharedViewModel.showSuggestions(newText) }
                return true
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let { goToCompare(query) }
                return true
            }
        })
    }

    private fun initAdapter() {
        val glide = GlideApp.with(this)
        adapter = SuggestionListAdapter(glide, onSuggestionClickListener())
        binding.recyclerView.addItemDecoration(
            DividerItemDecoration(this.context, DividerItemDecoration.VERTICAL)
        )
        binding.recyclerView.adapter = adapter
    }

    private fun initSwipeToRefresh() {
        binding.swipeRefresh.setOnRefreshListener { sharedViewModel.getProductSuggestions() }
    }

    private fun onSuggestionClickListener() = SuggestionListAdapter.OnClickListener { view, item ->
        binding.searchView.setQuery(item.suggestion, false)
        goToCompare(item.suggestion)
    }

    private fun goToCompare(query: String) {
        binding.searchView.clearFocus()
        val bundle = bundleOf(
            KEY_SHOP_ID to null,
            KEY_QUERY to query
        )
        findNavController().navigate(R.id.action_suggestion_to_compare, bundle)
    }
}