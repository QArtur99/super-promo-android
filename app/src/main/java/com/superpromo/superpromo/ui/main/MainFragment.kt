package com.superpromo.superpromo.ui.main

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.superpromo.superpromo.R
import com.superpromo.superpromo.databinding.FragmentStartBinding
import com.superpromo.superpromo.ui.util.EventObserver
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment() {

    private val sharedShopVm: SharedShopVm by viewModels({ requireActivity() })
    private val sharedSuggestionVm: SharedSuggestionVm by viewModels({ requireActivity() })
    private val sharedDrawerVm: SharedDrawerVm by viewModels({ requireActivity() })
    private val movieDetailViewModel: MainViewModel by viewModels()
    private lateinit var binding: FragmentStartBinding
    private val activityCompat by lazy { activity as AppCompatActivity }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStartBinding.inflate(inflater)
        activityCompat.setSupportActionBar(binding.appBar.toolbar)
        activityCompat.title = ""
        binding.appBar.toolbar.setNavigationIcon(R.drawable.ic_baseline_menu_24)

        binding.offer.setOnClickListener {
            sharedShopVm.showShops("")
            findNavController().navigate(R.id.action_start_to_offer)
        }
        binding.compare.setOnClickListener {
            sharedSuggestionVm.showSuggestions("")
            findNavController().navigate(R.id.action_start_to_suggestion)
        }
        binding.shoppingList.setOnClickListener {
            Toast.makeText(context, "Not Available", Toast.LENGTH_SHORT).show()
//            val intent = Intent(context, WebViewActivity::class.java)
//            intent.putExtra(WebViewActivity.ACTION_GO_TO_URL, "")
//            activity?.startActivityForResult(intent, WebViewActivity.ACTION_RESULT)
        }
        binding.drawer.addDrawerListener(object : DrawerLayout.DrawerListener {
            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
//                TODO("Not yet implemented")
            }

            override fun onDrawerOpened(drawerView: View) {
//                setStatusBarDark()
//                sharedDrawerVm.onOpenedEnd()
            }

            override fun onDrawerClosed(drawerView: View) {
//                setStatusBarTransparent()
            }

            override fun onDrawerStateChanged(newState: Int) {
//                TODO("Not yet implemented")
            }
        })
        sharedDrawerVm.onCloseEndClick.observe(viewLifecycleOwner, EventObserver {
            binding.drawer.closeDrawer(GravityCompat.END)
        })
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.filter, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_favorite -> {
                if (binding.drawer.isDrawerOpen(GravityCompat.END)) {
                    binding.drawer.closeDrawer(GravityCompat.END)
//                    setStatusBarTransparent()
                } else {
                    binding.drawer.openDrawer(GravityCompat.END)
//                    setStatusBarDark()
                }
            }
            android.R.id.home -> {
                if (binding.drawer.isDrawerOpen(GravityCompat.START)) {
                    binding.drawer.closeDrawer(GravityCompat.START)
//                    setStatusBarTransparent()
                } else {
                    binding.drawer.openDrawer(GravityCompat.START)
//                    setStatusBarDark()
                }
            }
            else -> return super.onOptionsItemSelected(item)
        }
        return true
    }

}