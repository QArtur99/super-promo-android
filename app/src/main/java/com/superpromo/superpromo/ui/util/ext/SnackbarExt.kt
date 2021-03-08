package com.superpromo.superpromo.ui.util.ext

import android.app.Activity
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar

fun Activity.snackbarShort(text: Any) = snackbar(this, text, Toast.LENGTH_SHORT)
fun Activity.snackbarLong(text: Any) = snackbar(this, text, Toast.LENGTH_LONG)

fun Fragment.snackbarShort(text: Any) = snackbar(requireActivity(), text, Toast.LENGTH_SHORT)
fun Fragment.snackbarLong(text: Any) = snackbar(requireActivity(), text, Toast.LENGTH_LONG)

fun snackbar(activity: Activity, text: Any, duration: Int) {
    val msg = when (text) {
        is String -> text
        is Int -> activity.getString(text)
        else -> ""
    }
    val snackbar = Snackbar.make(activity.window.decorView, msg, duration)
    snackbar.show()
}
