package com.superpromo.superpromo.ui.card_add

import android.os.Bundle
import android.view.*
import android.widget.RelativeLayout
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.superpromo.superpromo.GlideApp
import com.superpromo.superpromo.R
import com.superpromo.superpromo.data.db.model.CardDb
import com.superpromo.superpromo.databinding.FragmentCardAddBinding
import com.superpromo.superpromo.ui.card_add.color_adapter.CardColorListAdapter
import com.superpromo.superpromo.ui.data.CardColorModel
import com.superpromo.superpromo.ui.main.SharedDrawerVm
import com.superpromo.superpromo.ui.util.ext.hideSoftKeyBoard
import com.superpromo.superpromo.ui.util.ext.setNavigationResult
import com.superpromo.superpromo.ui.util.ext.setToolbar
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class CardAddFragment : Fragment() {

    companion object {
        const val KEY_CARD = "card"
    }

    private val sharedDrawerVm: SharedDrawerVm by viewModels({ requireActivity() })
    private val cardAddViewModel: CardAddViewModel by viewModels()

    private val bundle: CardAddFragmentArgs by navArgs()
    private lateinit var binding: FragmentCardAddBinding
    private lateinit var cardDb: CardDb

    private var selectedName: String? = null
    private var selectedView: View? = null
    private var selectedColor: CardColorModel? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCardAddBinding.inflate(inflater)
        setToolbar(binding.appBar.toolbar)
        bundle.card?.let {
            cardDb = it
            binding.name.setText(it.name)
            binding.number.setText(it.number)
        }

        setColorCardAdapter()
        saveCard()

        setHasOptionsMenu(true)
        return binding.root
    }

    private fun setColorCardAdapter() {
        val glide = GlideApp.with(this)
        val adapter = CardColorListAdapter(glide, onCardColorClickListener())
        binding.recyclerView.adapter = adapter
        cardAddViewModel.cardColorList.observe(viewLifecycleOwner, { list ->
            findSelectedColor(list)
            adapter.submitList(list)
        })
    }

    private fun findSelectedColor(it: List<CardColorModel>) {
        if (cardDb.color.isNotEmpty()) {
            for (i in it.indices) {
                if (getString(it[i].color) == cardDb.color) {
                    it[i].click = true
                    break
                }
            }
        } else {
            it[0].click = true
        }
    }

    private fun onCardColorClickListener() =
        CardColorListAdapter.OnClickListener { view, cardColor ->
            var selector = selectedView?.findViewById<RelativeLayout>(R.id.selector)
            selector?.visibility = View.GONE
            selectedView = view
            cardDb = cardDb.copy(color = getString(cardColor.color))
            selector = view.findViewById(R.id.selector)
            selector.visibility = View.VISIBLE
            activity?.hideSoftKeyBoard(binding.root)
        }

    private fun saveCard() {
        binding.save.setOnClickListener {
            if (isCorrect()) {
                cardDb = cardDb.copy(
                    name = binding.name.text.toString(),
                    number = binding.number.text.toString()
                )
                cardAddViewModel.addCard(cardDb)
                val bundle = bundleOf(
                    KEY_CARD to cardDb,
                )
                activity?.hideSoftKeyBoard(binding.root)
                setNavigationResult(bundle)
            }
        }
    }

    private fun isCorrect(): Boolean {
        var result = true
        if (binding.name.text.toString().isEmpty()) {
            binding.name.error = getString(R.string.card_barcode_dialog_name_title)
            result = false
        }
        if (binding.number.text.toString().isEmpty()) {
            binding.number.error = getString(R.string.card_barcode_dialog_number_title)
            result = false
        }
        return result

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        super.onCreateOptionsMenu(menu, inflater)
    }
}