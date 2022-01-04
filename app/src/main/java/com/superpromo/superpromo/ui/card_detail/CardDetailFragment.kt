package com.superpromo.superpromo.ui.card_detail

import android.graphics.Color
import android.os.Bundle
import android.view.*
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.zxing.BarcodeFormat
import com.google.zxing.integration.android.IntentIntegrator
import com.journeyapps.barcodescanner.BarcodeEncoder
import com.superpromo.superpromo.R
import com.superpromo.superpromo.data.db.model.CardDb
import com.superpromo.superpromo.databinding.FragmentCardDetailBinding
import com.superpromo.superpromo.ui.CustomCaptureActivity
import com.superpromo.superpromo.ui.card_add.CardAddFragment
import com.superpromo.superpromo.ui.main.SharedDrawerVm
import com.superpromo.superpromo.ui.util.ext.onNavBackStackListener
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
    private lateinit var cardDb: CardDb

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCardDetailBinding.inflate(inflater)
        setToolbar(binding.appBar.toolbar)
        onNavigationResult()

        getBundle()

        setHasOptionsMenu(true)
        return binding.root
    }

    private fun onNavigationResult() {
        onNavBackStackListener {
            if (it.containsKey(CardAddFragment.KEY_CARD)) {
                val cardDb = it.get(CardAddFragment.KEY_CARD) as CardDb
                setCard(cardDb)
            }
        }
    }

    private fun getBundle() {
        bundle.card?.let {
            setCard(it)
        }
    }

    private fun setCard(cardDb: CardDb) {
        this.cardDb = cardDb
        binding.editText.text = cardDb.name
        binding.code.text = cardDb.number
        binding.background.setBackgroundColor(Color.parseColor(cardDb.color))
        generateBarcode(cardDb)
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

    private fun scanCard() {
        val scanIntegrator = IntentIntegrator.forSupportFragment(this)
        scanIntegrator.captureActivity = CustomCaptureActivity::class.java
        scanIntegrator.setPrompt("")
        scanIntegrator.setOrientationLocked(true)
        scanIntegrator.initiateScan()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.card, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_edit -> onEdit()
            R.id.action_delete -> onDelete()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun onEdit() {
        val bundle = bundleOf(
            CardAddFragment.KEY_CARD to cardDb,
        )
        findNavController().navigate(R.id.action_to_card_add, bundle)
    }

    private fun onDelete() {
        cardDetailViewModel.deleteCard(cardDb.id)
        activity?.onBackPressed()
    }
}
