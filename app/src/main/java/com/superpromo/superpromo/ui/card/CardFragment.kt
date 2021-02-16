package com.superpromo.superpromo.ui.card

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.zxing.integration.android.IntentIntegrator
import com.superpromo.superpromo.GlideApp
import com.superpromo.superpromo.R
import com.superpromo.superpromo.databinding.DialogCardColorBinding
import com.superpromo.superpromo.databinding.DialogCardNameBinding
import com.superpromo.superpromo.databinding.FragmentCardBinding
import com.superpromo.superpromo.ui.CustomCaptureActivity
import com.superpromo.superpromo.ui.card.adapter.CardListAdapter
import com.superpromo.superpromo.ui.card.color_adapter.CardColorListAdapter
import com.superpromo.superpromo.ui.main.SharedDrawerVm
import com.superpromo.superpromo.ui.util.ext.setToolbar
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

        observeMenuList()

        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            if (result.contents == null) {
                Toast.makeText(requireActivity(), "Cancelled", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(requireActivity(), "Success:" + result.contents, Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun observeMenuList() {
        cardViewModel.cardList.observe(viewLifecycleOwner, {
            adapter.submitList(it)
        })
    }

    private fun initAdapter() {
        val glide = GlideApp.with(this)
        adapter = CardListAdapter(glide, onShopClickListener())
        binding.recyclerView.adapter = adapter
    }

    private fun onShopClickListener() = CardListAdapter.OnClickListener { view, card ->
        when (card.background) {
            R.drawable.ic_baseline_add_circle_24 -> {
                addCardName()
            }
            else -> {

            }
        }
    }

    private fun addCardName() {
        val bindingDialog = DialogCardNameBinding.inflate(layoutInflater)
        val dialog = AlertDialog.Builder(requireContext())
            .setTitle(R.string.card_barcode_dialog_name_title)
            .setView(bindingDialog.root)
            .setPositiveButton(R.string.common_btn_ok) { _, _ ->
                addCardColor()
            }
            .setNegativeButton(R.string.common_btn_cancel) { _, _ ->
            }
            .create()
        dialog.show()
    }

    private fun addCardColor() {
        val bindingDialog = DialogCardColorBinding.inflate(layoutInflater)
        setColorCardAdapter(bindingDialog)
        val dialog = AlertDialog.Builder(requireContext())
            .setTitle(R.string.card_barcode_dialog_name_title)
            .setView(bindingDialog.root)
            .setPositiveButton(R.string.common_btn_ok) { _, _ ->
                scanCard()
            }
            .setNegativeButton(R.string.common_btn_cancel) { _, _ ->
            }
            .create()
        dialog.show()
    }

    private fun setColorCardAdapter(binding: DialogCardColorBinding) {
        val glide = GlideApp.with(this)
        val adapter = CardColorListAdapter(glide, onCardColorClickListener())
        binding.recyclerView.adapter = adapter
        cardViewModel.cardColorList.observe(viewLifecycleOwner, {
            adapter.submitList(it)
        })
    }

    private fun onCardColorClickListener() = CardColorListAdapter.OnClickListener { view, card ->

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