package com.superpromo.superpromo.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.superpromo.superpromo.databinding.FragmentStartBinding
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
//        val binding: FragmentStartBinding = DataBindingUtil.inflate(
//            inflater, R.layout.fragment_start, container, false
//        )
//        binding.movieDetailViewModel = movieDetailViewModel
//        binding.lifecycleOwner = this
        return binding.root
    }
}