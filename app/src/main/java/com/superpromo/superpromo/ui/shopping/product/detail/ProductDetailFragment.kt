package com.superpromo.superpromo.ui.shopping.product.detail

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.core.widget.ImageViewCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.superpromo.superpromo.GlideApp
import com.superpromo.superpromo.GlideRequests
import com.superpromo.superpromo.R
import com.superpromo.superpromo.data.db.model.ProductDb
import com.superpromo.superpromo.databinding.FragmentShoppingProductDetailBinding
import com.superpromo.superpromo.ui.WebViewActivity
import com.superpromo.superpromo.ui.util.GlideHelper
import com.superpromo.superpromo.ui.util.ext.setToolbar
import com.superpromo.superpromo.ui.util.ext.toStringN
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductDetailFragment : Fragment() {

    companion object {
        const val KEY_PRODUCT = "product"
    }

    private val glide: GlideRequests by lazy { GlideApp.with(this) }
    private val productDetailViewModel: ProductDetailViewModel by viewModels()
    private lateinit var binding: FragmentShoppingProductDetailBinding
    private val bundle: ProductDetailFragmentArgs by navArgs()

    private lateinit var _menu: Menu
    private lateinit var _productDb: ProductDb

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentShoppingProductDetailBinding.inflate(inflater)
        setToolbar(binding.appBar.toolbar)

        bundle.product?.let {
            _productDb = it
            setView(it)
            hideEmptyView(it)
            setListeners()
            productDetailViewModel.setProductId(it.id)
        }

        productDetailViewModel.showAdd.observe(
            viewLifecycleOwner,
            {
                _menu.findItem(R.id.action_add).isVisible = true
                _menu.findItem(R.id.action_delete).isVisible = false
            }
        )

        productDetailViewModel.showDelete.observe(
            viewLifecycleOwner,
            {
                _menu.findItem(R.id.action_add).isVisible = false
                _menu.findItem(R.id.action_delete).isVisible = true
            }
        )

        productDetailViewModel.productIdNew.observe(
            viewLifecycleOwner,
            {
                _productDb = _productDb.copy(id = it)
            }
        )

        setHasOptionsMenu(true)
        return binding.root
    }

    private fun setView(item: ProductDb) {
        item.imgUrl?.let {
            GlideHelper.bingImg(binding.productImg, glide, it)
            ImageViewCompat.setImageTintList(binding.productImg, null)
        }
        binding.shopName.text = item.shopName
        binding.productName.text = item.name
        binding.productNameSub.text = item.subtitle
        binding.price.text = item.price.toString()
        binding.amount.text = item.amount
        binding.details.text = item.details
        binding.promo.text = item.promo
        binding.promoInfo.text = item.promoInfo
        binding.url.text = item.url
    }

    private fun hideEmptyView(item: ProductDb) {
        if (item.shopName.isNullOrEmpty()) {
            binding.shopNameHint.visibility = View.GONE
            binding.shopName.visibility = View.GONE
        }
        if (item.name.isNullOrEmpty()) {
            binding.productNameHint.visibility = View.GONE
            binding.productName.visibility = View.GONE
        }
        if (item.subtitle.isNullOrEmpty()) {
            binding.productNameSubHint.visibility = View.GONE
            binding.productNameSub.visibility = View.GONE
        }
        if (item.price.toStringN().isNullOrEmpty()) {
            binding.priceHint.visibility = View.GONE
            binding.price.visibility = View.GONE
        }
        if (item.amount.isNullOrEmpty()) {
            binding.amountHint.visibility = View.GONE
            binding.amount.visibility = View.GONE
        }
        if (item.details.isNullOrEmpty()) {
            binding.detailsHint.visibility = View.GONE
            binding.details.visibility = View.GONE
        }
        if (item.promo.isNullOrEmpty()) {
            binding.promoHint.visibility = View.GONE
            binding.promo.visibility = View.GONE
        }
        if (item.promoInfo.isNullOrEmpty()) {
            binding.promoInfoHint.visibility = View.GONE
            binding.promoInfo.visibility = View.GONE
        }
        if (item.url.isNullOrEmpty()) {
            binding.urlHint.visibility = View.GONE
            binding.url.visibility = View.GONE
        }
    }

    private fun setListeners() {
        binding.url.setOnClickListener {
            val textView = it as TextView
            val intent = Intent(context, WebViewActivity::class.java)
            intent.putExtra(WebViewActivity.ACTION_GO_TO_URL, textView.text)
            activity?.startActivityForResult(intent, WebViewActivity.ACTION_RESULT)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        _menu = menu
        menu.clear()
        inflater.inflate(R.menu.shopping_list_product_detail, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_add -> onAdd()
            R.id.action_delete -> onDelete()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun onAdd() {
        productDetailViewModel.insertProductDb(_productDb)
    }

    private fun onDelete() {
        productDetailViewModel.deleteProductDb(_productDb)
    }
}
