package com.superpromo.superpromo.ui.card_detail

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.google.zxing.BarcodeFormat
import com.google.zxing.integration.android.IntentIntegrator
import com.journeyapps.barcodescanner.BarcodeEncoder
import com.superpromo.superpromo.GlideApp
import com.superpromo.superpromo.R
import com.superpromo.superpromo.data.db.model.CardDb
import com.superpromo.superpromo.databinding.DialogCardColorBinding
import com.superpromo.superpromo.databinding.DialogCardNameBinding
import com.superpromo.superpromo.databinding.FragmentCardDetailBinding
import com.superpromo.superpromo.ui.CustomCaptureActivity
import com.superpromo.superpromo.ui.card.color_adapter.CardColorListAdapter
import com.superpromo.superpromo.ui.data.CardColorModel
import com.superpromo.superpromo.ui.main.SharedDrawerVm
import com.superpromo.superpromo.ui.util.ext.setToolbar
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class CardDetailFragment : Fragment() {

    companion object {
        const val KEY_CARD = "card"
    }

    private val sharedDrawerVm: SharedDrawerVm by viewModels({ requireActivity() })
    private val cardDetailViewModel: CardDetailViewModel by viewModels()

    private val bundle: CardDetailFragmentArgs by navArgs()
    private lateinit var binding: FragmentCardDetailBinding

    private var selectedName: String? = null
    private var selectedView: View? = null
    private var selectedColor: CardColorModel? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCardDetailBinding.inflate(inflater)
        setToolbar(binding.appBar.toolbar)
        bundle.card?.let {
            binding.editText.text = it.name
            binding.code.text = it.number
            binding.background.setBackgroundColor(Color.parseColor(it.color))
            generateBarcode(it)
        }
        setHasOptionsMenu(true)
        return binding.root
    }


    private fun generateBarcode(cardDb: CardDb) {
        try {
            if (cardDb.formatName.isNotEmpty()) {
                val barcodeEncoder = BarcodeEncoder()
                val bitmap = barcodeEncoder.encodeBitmap(
                    cardDb.number,
                    BarcodeFormat.valueOf(cardDb.formatName),
                    600,
                    200
                )
                binding.barcode.setImageBitmap(bitmap)
            }
        } catch (e: Exception) {
        }
    }

    private fun addCardName() {
        val bindingDialog = DialogCardNameBinding.inflate(layoutInflater)
        val dialog = AlertDialog.Builder(requireContext())
            .setTitle(R.string.card_barcode_dialog_name_title)
            .setView(bindingDialog.root)
            .setPositiveButton(R.string.common_btn_ok) { _, _ ->
                selectedName = bindingDialog.editText.text.toString()
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
            .setTitle(R.string.card_barcode_dialog_color_title)
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
        cardDetailViewModel.cardColorList.observe(viewLifecycleOwner, {
            adapter.submitList(it)
        })
    }

    private fun onCardColorClickListener() =
        CardColorListAdapter.OnClickListener { view, cardColor ->
            var selector = selectedView?.findViewById<RelativeLayout>(R.id.selector)
            selector?.visibility = View.GONE
            selectedView = view
            selectedColor = cardColor
            selector = view.findViewById(R.id.selector)
            selector.visibility = View.VISIBLE
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