package com.superpromo.superpromo.ui.compare.fromOffer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import com.superpromo.superpromo.GlideApp
import com.superpromo.superpromo.databinding.FragmentSuggestionBinding
import com.superpromo.superpromo.repository.state.State
import com.superpromo.superpromo.ui.compare.adapter.suggestion.SuggestionListAdapter
import com.superpromo.superpromo.ui.compare.fromMain.CompareFromMainFragment.Companion.KEY_SHOP_ID
import com.superpromo.superpromo.ui.main.SharedSuggestionVm
import com.superpromo.superpromo.ui.util.ext.setNavigationResult
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SuggestionFromOfferFragment : Fragment() {

    companion object {
        const val KEY_QUERY = "query"
    }

    private val sharedSuggestionVm: SharedSuggestionVm by viewModels({ requireActivity() })
    private lateinit var binding: FragmentSuggestionBinding
    private lateinit var adapter: SuggestionListAdapter
    private val bundle: SuggestionFromOfferFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSuggestionBinding.inflate(inflater)
        initQuery()
        initAdapter()
        initSwipeToRefresh()
        bundle.query?.let {
            binding.appBar.searchView.setQuery(it, false)
            binding.appBar.searchView.requestFocus()
        }

        observeSuggestions()

        setHasOptionsMenu(true)
        return binding.root
    }

    private fun observeSuggestions() {
        sharedSuggestionVm.suggestions.observe(viewLifecycleOwner, {
            when (it.state) {
                is State.Loading -> {
                    binding.swipeRefresh.isRefreshing = true
                    binding.noConnection.noConnection.visibility = View.GONE
                    binding.emptyView.emptyView.visibility = View.GONE
                }
                is State.Success -> {
                    adapter.submitList(it.suggestionList)
                    binding.swipeRefresh.isRefreshing = false
                    if (it.suggestionList.isEmpty()) {
                        binding.noConnection.noConnection.visibility = View.GONE
                        binding.emptyView.emptyView.visibility = View.VISIBLE
                    } else {
                        binding.noConnection.noConnection.visibility = View.GONE
                        binding.emptyView.emptyView.visibility = View.GONE
                    }
                }
                is State.Error -> {
                    binding.swipeRefresh.isRefreshing = false
                    if (it.suggestionList.isEmpty()) {
                        binding.noConnection.noConnection.visibility = View.VISIBLE
                        binding.emptyView.emptyView.visibility = View.GONE
                    } else {
                        binding.noConnection.noConnection.visibility = View.GONE
                        binding.emptyView.emptyView.visibility = View.GONE
                    }
                }
            }
        })
    }

    private fun initQuery() {
        binding.appBar.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let { sharedSuggestionVm.showSuggestions(newText) }
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
        binding.swipeRefresh.setOnRefreshListener { sharedSuggestionVm.getProductSuggestions() }
    }

    private fun onSuggestionClickListener() = SuggestionListAdapter.OnClickListener { view, item ->
        binding.appBar.searchView.setQuery(item.name, false)
        goToCompare(item.name)
    }

    private fun goToCompare(query: String) {
        binding.appBar.searchView.clearFocus()
        val bundle = bundleOf(
            KEY_SHOP_ID to null,
            KEY_QUERY to query
        )
        setNavigationResult(bundle)
    }
}