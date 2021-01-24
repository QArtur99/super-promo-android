package com.superpromo.superpromo.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.superpromo.superpromo.R
import com.superpromo.superpromo.databinding.FragmentStartBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment() {

    private val sharedSuggestionVm: SharedSuggestionVm by viewModels({ requireActivity() })
    private val movieDetailViewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentStartBinding.inflate(inflater)
        binding.offer.setOnClickListener {
            findNavController().navigate(R.id.action_start_to_offer)
        }
        binding.compare.setOnClickListener {
            sharedSuggestionVm.suggestions.observe(viewLifecycleOwner, {})
            sharedSuggestionVm.showSuggestions("")
            findNavController().navigate(R.id.action_start_to_suggestion)
        }
        binding.shoppingList.setOnClickListener {
            Toast.makeText(context, "Not Available", Toast.LENGTH_SHORT).show()
//            val intent = Intent(context, WebViewActivity::class.java)
//            intent.putExtra(WebViewActivity.ACTION_GO_TO_URL, "")
//            activity?.startActivityForResult(intent, WebViewActivity.ACTION_RESULT)
        }
        return binding.root
    }
}