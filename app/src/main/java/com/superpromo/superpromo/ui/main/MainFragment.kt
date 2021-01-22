package com.superpromo.superpromo.ui.main

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.superpromo.superpromo.R
import com.superpromo.superpromo.databinding.FragmentStartBinding
import com.superpromo.superpromo.ui.WebViewActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment() {

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
            findNavController().navigate(R.id.action_start_to_suggestion)
        }
        binding.shoppingList.setOnClickListener {
            val intent = Intent(context, WebViewActivity::class.java)
            intent.putExtra(WebViewActivity.ACTION_GO_TO_URL, "")
            activity?.startActivityForResult(intent, WebViewActivity.ACTION_RESULT)
        }
        return binding.root
    }
}