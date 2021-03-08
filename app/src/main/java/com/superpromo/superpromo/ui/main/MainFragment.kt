package com.superpromo.superpromo.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.superpromo.superpromo.R
import com.superpromo.superpromo.databinding.FragmentStartBinding
import com.superpromo.superpromo.ui.util.ext.setToolbarHome
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment() {

    private val sharedShopVm: SharedShopVm by viewModels({ requireActivity() })
    private val sharedSuggestionVm: SharedSuggestionVm by viewModels({ requireActivity() })
    private val movieDetailViewModel: MainViewModel by viewModels()
    private lateinit var binding: FragmentStartBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStartBinding.inflate(inflater)
        setToolbarHome(binding.appBar.toolbar)

        binding.offer.setOnClickListener {
            sharedShopVm.showShops("")
            findNavController().navigate(R.id.action_start_to_offer)
        }
        binding.compare.setOnClickListener {
            sharedSuggestionVm.showSuggestions("")
            findNavController().navigate(R.id.action_start_to_suggestion)
        }
        binding.shoppingList.setOnClickListener {
            findNavController().navigate(R.id.action_start_to_shopping_list)
//            Toast.makeText(context, "Not Available", Toast.LENGTH_SHORT).show()
//            val intent = Intent(context, WebViewActivity::class.java)
//            intent.putExtra(WebViewActivity.ACTION_GO_TO_URL, "")
//            activity?.startActivityForResult(intent, WebViewActivity.ACTION_RESULT)
        }
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.main, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_card -> onCard()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun onCard() {
        findNavController().navigate(R.id.action_start_to_card)
    }
}
