package com.superpromo.superpromo.ui.util.ext

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

fun Context.showSoftKeyBoard(v: View) {
    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
    imm?.showSoftInput(v,0)
}

fun Context.hideSoftKeyBoard(v: View) {
    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
    imm?.hideSoftInputFromWindow(v.windowToken,0)
}