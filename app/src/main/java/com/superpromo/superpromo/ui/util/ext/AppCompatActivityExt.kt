package com.superpromo.superpromo.ui.util.ext

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.superpromo.superpromo.R

fun AppCompatActivity.addFragment(fragment: Fragment, frameId: Int) {
    supportFragmentManager.inTransaction { add(frameId, fragment) }
}

fun AppCompatActivity.replaceFragment(fragment: Fragment, frameId: Int) {
    supportFragmentManager.inTransaction { replace(frameId, fragment) }
}

fun AppCompatActivity.removeFragment(fragment: Fragment) {
    supportFragmentManager.inTransaction { remove(fragment) }
}

fun AppCompatActivity.setToolbar(toolbar: Toolbar) {
    setSupportActionBar(toolbar)
    title = ""
    toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24)
    toolbar.setNavigationOnClickListener {
        if (hideSoftKeyBoard(it) == false) {
            onBackPressed()
        }
        it.rootView.clearFocus()
    }
}