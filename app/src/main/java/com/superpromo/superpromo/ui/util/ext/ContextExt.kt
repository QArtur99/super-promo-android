package com.superpromo.superpromo.ui.util.ext

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

fun Context.showSoftKeyBoard(v: View): Boolean? {
    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
    return imm?.showSoftInput(v, 0)
}

fun Context.hideSoftKeyBoard(v: View): Boolean? {
    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
    val result = imm?.hideSoftInputFromWindow(v.windowToken, 0)
    v.rootView.clearFocus()
    return result
}
