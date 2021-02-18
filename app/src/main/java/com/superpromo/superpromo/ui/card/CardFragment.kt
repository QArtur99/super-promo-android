package com.superpromo.superpromo.ui.card

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.zxing.integration.android.IntentIntegrator
import com.superpromo.superpromo.GlideApp
import com.superpromo.superpromo.R
import com.superpromo.superpromo.data.db.model.CardDb
import com.superpromo.superpromo.databinding.DialogCardColorBinding
import com.superpromo.superpromo.databinding.DialogCardNameBinding
import com.superpromo.superpromo.databinding.FragmentCardBinding
import com.superpromo.superpromo.ui.CustomCaptureActivity
import com.superpromo.superpromo.ui.card.adapter.CardListAdapter
import com.superpromo.superpromo.ui.card_add.color_adapter.CardColorListAdapter
import com.superpromo.superpromo.ui.card_add.CardAddFragment
import com.superpromo.superpromo.ui.card_detail.CardDetailFragment
import com.superpromo.superpromo.ui.data.CardColorModel
import com.superpromo.superpromo.ui.main.SharedDrawerVm
import com.superpromo.superpromo.ui.util.ext.setToolbar
import com.superpromo.superpromo.ui.util.ext.toast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CardFragment : Fragment() {

    private val sharedDrawerVm: SharedDrawerVm by viewModels({ requireActivity() })
    private val cardViewModel: CardViewModel by viewModels()

    private lateinit var binding: FragmentCardBinding
    private lateinit var adapter: CardListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCardBinding.inflate(inflater)
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
        cardViewModel.cardList.observe(viewLifecycleOwner, {
            binding.swipeRefresh.isRefreshing = false
            adapter.submitList(it)
        })
    }

    private fun initAdapter() {
        val glide = GlideApp.with(this)
        adapter = CardListAdapter(glide, onShopClickListener())
        binding.recyclerView.adapter = adapter
    }

    private fun initSwipeToRefresh() {
        binding.swipeRefresh.setOnRefreshListener {
//            cardViewModel.getCardList()
            binding.swipeRefresh.isRefreshing = false
        }
    }

    private fun onShopClickListener() = CardListAdapter.OnClickListener { view, card ->
        when (card.name) {
            "" -> scanCard()
            else -> {
                val bundle = bundleOf(
                    CardDetailFragment.KEY_CARD to card,
                )
                findNavController().navigate(R.id.action_to_card_detail, bundle)

            }
        }
    }

    private fun scanCard() {
        val scanIntegrator = IntentIntegrator.forSupportFragment(this)
        scanIntegrator.captureActivity = CustomCaptureActivity::class.java
        scanIntegrator.setPrompt("")
        scanIntegrator.setOrientationLocked(true)
        scanIntegrator.initiateScan()
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        menu.clear()
    }
}