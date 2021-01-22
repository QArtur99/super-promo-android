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
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import com.superpromo.superpromo.GlideApp
import com.superpromo.superpromo.databinding.FragmentCompareBinding
import com.superpromo.superpromo.databinding.FragmentSuggestionBinding
import com.superpromo.superpromo.ui.main.SharedSuggestionVm
import com.superpromo.superpromo.ui.suggestion.suggestion.SuggestionListAdapter
import com.superpromo.superpromo.ui.util.ext.setNavigationResult
import com.superpromo.superpromo.ui.util.ext.showSoftKeyBoard
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SuggestionFragment : Fragment() {

    companion object {
        const val KEY_SUGGESTION = "KEY_SUGGESTION"
        const val KEY_QUERY = "query"
    }

    private val sharedViewModel: SharedSuggestionVm by viewModels({ requireActivity() })
    private lateinit var binding: FragmentSuggestionBinding
    private lateinit var adapter: SuggestionListAdapter
    private val bundle: SuggestionFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSuggestionBinding.inflate(inflater)

        initQuery()
        initAdapter()
        initSwipeToRefresh()
        sharedViewModel.suggestions.observe(viewLifecycleOwner, {
            adapter.submitList(it)
            binding.swipeRefresh.isRefreshing = false
        })
        bundle.query?.let {
            binding.searchView.setQuery(it, false)
        }
        binding.searchView.doOnAttach {
            binding.searchView.requestFocus()
//            context?.showSoftKeyBoard(binding.searchView)
        }
        return binding.root
    }

    private fun initQuery() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let { sharedViewModel.showSuggestions(newText) }
                return true
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    setNavigationResult(bundleOf(KEY_SUGGESTION to query))
                }
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
        setNavigationResult(bundleOf(KEY_SUGGESTION to item.suggestion))
    }
}